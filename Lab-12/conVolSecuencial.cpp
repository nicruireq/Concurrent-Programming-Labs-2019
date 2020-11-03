/**
 * conVolSecuencia.cpp
 * 
 * @author Nicolas Ruiz Requejo
 **/

#include <iostream>
#include <random>
#include <chrono>

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

/**
     * Computes 2D convolution of a matrix with a 3 by 3 kernel
     * 
     * @param Conv  convolved matrix n by m
     * @param A matrix n by m
     * @param h 3 by 3 convolution kernel
     * @param dimN  number of rows
     * @param dimM  number of columns
     * @return convoluted matrix
     */

void conv2DKernel3x3(int Conv[][M], int A[][M], int h[][3], int n, int m)
{
    for (int i = 1; i < (n - 1); i++)
    {
        for (int j = 1; j < (m - 1); j++)
        {
            for (int rowoffset = -1; rowoffset <= 1; rowoffset++)
            {
                for (int coloffset = -1; coloffset <= 1; coloffset++)
                {
                    Conv[i][j] += A[i + rowoffset][j + coloffset] * h[1 + rowoffset][1 + coloffset];
                }
            }
        }
    }
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

int enfoque[][3] = {{0, -1, 0}, {-1, 5, -1}, {0, -1, 0}};
int realzaBordes[][3] = {{0, 0, 0}, {-1, 1, 0}, {0, 0, 0}};
int detectaBordes[][3] = {{0, 1, 0}, {1, -4, 1}, {0, 1, 0}};
int sobel[][3] = {{-1, 0, 1}, {-2, 0, 2}, {-1, 0, 1}};
int sharpen[][3] = {{1, -2, 1}, {-2, 5, -2}, {1, -2, 1}};
int prueba[][5] = {{35, 40, 41, 45, 50}, {40, 40, 42, 46, 52}, {42, 46, 50, 55, 55}, {48, 52, 56, 58, 60}, {56, 60, 65, 70, 75}};
int kernelprueba[][3] = {{-2, -1, 0}, {-1, 1, 1}, {0, 1, 2}};

int main(int argc, char const *argv[])
{
    using namespace std::chrono;

    std::cout << "1. Enfoque" << std::endl;
    std::cout << "2. Realzar bordes" << std::endl;
    std::cout << "3. Detectar bordes" << std::endl;
    std::cout << "4. Sobel" << std::endl;
    std::cout << "5. Sharpen" << std::endl;
    std::cout << "6. Prueba" << std::endl;
    std::cout << "Elija un kernel: " << std::endl;
    int op = 0;
    std::cin >> op;

    int matriz[N][M];
    static int conv[N][M];
    static int convprueba[5][5];
    rellenoAleatorio(matriz, N, M);

    high_resolution_clock::time_point t1 = high_resolution_clock::now();

    switch (op)
    {
    case 1:
        conv2DKernel3x3(conv, matriz, enfoque, N, M);
        break;
    case 2:
        conv2DKernel3x3(conv, matriz, realzaBordes, N, M);
        break;
    case 3:
        conv2DKernel3x3(conv, matriz, detectaBordes, N, M);
        break;
    case 4:
        conv2DKernel3x3(conv, matriz, sobel, N, M);
        break;
    case 5:
        conv2DKernel3x3(conv, matriz, sharpen, N, M);
        break;
        //case 6:
        //conv2DKernel3x3(convprueba, prueba, kernelprueba, 5, 5);
        //  break;
    default:
        break;
    }

    high_resolution_clock::time_point t2 = high_resolution_clock::now();
    duration<double, std::milli> time_span = duration_cast<duration<double, std::milli>>(t2 - t1);

    std::cout << "Tiempo de ejecucion: " << time_span.count() << " milisegundos." << std::endl;

    return 0;
}
