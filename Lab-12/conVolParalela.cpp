/**
 *  conVolParalela
 * 
 *  @author Nicolas Ruiz Requejo
 **/

#include <iostream>
#include <random>
#include <chrono>
#include <thread>
#include <vector>
#include <algorithm>

#define N 1000
#define M 1000

void rellenoAleatorio(int mat[][M], int dimN, int dimM)
{
    unsigned seed = std::chrono::system_clock::now().time_since_epoch().count();
    std::default_random_engine generator(seed);

    std::uniform_int_distribution<int> distribution(1, 100);

    for (int i = 0; i < dimN; ++i)
        for (int j = 0; j < dimM; ++j)
            mat[i][j] = distribution(generator);
}

void printMatrix(int mat[][M], int dimN, int dimM)
{
    std::cout << "[";
    for (size_t i = 0; i < dimN; i++)
    {
        for (size_t j = 0; j < dimM; j++)
        {
            std::cout << mat[i][j] << " ";
        }
        std::cout << std::endl;
    }
    std::cout << "]" << std::endl;
}

void copy2DArray(int src[3][3], int dst[3][3])
{
    for (size_t i = 0; i < 3; i++)
    {
        for (size_t j = 0; j < 3; j++)
        {
            dst[i][j] = src[i][j];
        }
    }
}

int enfoque[][3] = {{0, -1, 0}, {-1, 5, -1}, {0, -1, 0}};
int realzaBordes[][3] = {{0, 0, 0}, {-1, 1, 0}, {0, 0, 0}};
int detectaBordes[][3] = {{0, 1, 0}, {1, -4, 1}, {0, 1, 0}};
int sobel[][3] = {{-1, 0, 1}, {-2, 0, 2}, {-1, 0, 1}};
int sharpen[][3] = {{1, -2, 1}, {-2, 5, -2}, {1, -2, 1}};
int prueba[][5] = {{35, 40, 41, 45, 50}, {40, 40, 42, 46, 52}, {42, 46, 50, 55, 55}, {48, 52, 56, 58, 60}, {56, 60, 65, 70, 75}};
int kernelprueba[][3] = {{-2, -1, 0}, {-1, 1, 1}, {0, 1, 2}};

int A[N][M];
int h[3][3];
static int C[N][M];

class conVolParalela
{
private:
    int ini, fini;

public:
    conVolParalela(int ini, int fini)
        : ini(ini), fini(fini){};
    void operator()() const
    {
        for (int i = ini; i <= fini; i++)
        {
            for (int j = 1; j < (M - 1); j++)
            {
                for (int rowoffset = -1; rowoffset <= 1; rowoffset++)
                {
                    for (int coloffset = -1; coloffset <= 1; coloffset++)
                    {
                        C[i][j] += A[i + rowoffset][j + coloffset] * h[1 + rowoffset][1 + coloffset];
                    }
                }
            }
        }
    };
};

int main(int argc, char const *argv[])
{
    using namespace std::chrono;

    std::cout << "Numero de hilos: ";
    int numHilos;
    std::cin >> numHilos;

    std::cout << "1. Enfoque" << std::endl;
    std::cout << "2. Realzar bordes" << std::endl;
    std::cout << "3. Detectar bordes" << std::endl;
    std::cout << "4. Sobel" << std::endl;
    std::cout << "5. Sharpen" << std::endl;
    std::cout << "6. Prueba" << std::endl;
    std::cout << "Elija un kernel: " << std::endl;
    int op = 0;
    std::cin >> op;

    switch (op)
    {
    case 1:
        copy2DArray(enfoque, h);
        break;
    case 2:
        copy2DArray(realzaBordes, h);
        break;
    case 3:
        copy2DArray(detectaBordes, h);
        break;
    case 4:
        copy2DArray(sobel, h);
        break;
    case 5:
        copy2DArray(sharpen, h);
        break;
    case 6:
        copy2DArray(kernelprueba, h);
        break;
    default:
        break;
    }

    rellenoAleatorio(A, N, M);

    high_resolution_clock::time_point t1 = high_resolution_clock::now();
    //reparto
    std::vector<conVolParalela> tareas;
    int numTareas = (N - 2) / numHilos;
    int restoTareas = (N - 2) % numHilos;
    int i;
    int n = N - 2 - restoTareas; // filas evaluadas
    //std::cout << "numTareas = " << numTareas << std::endl;
    //std::cout << "resto = " << restoTareas << std::endl;
    //std::cout << "n = " << n << std::endl;
    for (i = 1; i <= (n - numTareas); i += numTareas)
    {
        //std::cout << "ini= " << i << " fin= " << ((i + numTareas) - 1) << std::endl;
        tareas.push_back(conVolParalela(i, (i + numTareas) - 1));
    }
    if (restoTareas != 0)
    {
        //std::cout << "RESTO ini= " << i << " fin= " << ((i + numTareas + restoTareas) - 1) << std::endl;
        tareas.push_back(conVolParalela(i, (i + numTareas + restoTareas) - 1));
    }
    else
    {
        //std::cout << "EXACTO ini= " << i << " fin= " << ((i + numTareas) - 1) << std::endl;
        tareas.push_back(conVolParalela(i, (i + numTareas) - 1));
    }

    // co-rutina
    std::vector<std::thread> hilos;
    for (conVolParalela &t : tareas)
    {
        hilos.push_back(std::thread(t));
    }

    for (std::thread &t : hilos)
    {
        t.join();
    }

    high_resolution_clock::time_point t2 = high_resolution_clock::now();
    duration<double, std::milli> time_span = duration_cast<duration<double, std::milli>>(t2 - t1);

    std::cout << "Tiempo de ejecucion: " << time_span.count() << " milisegundos." << std::endl;

    return 0;
}
