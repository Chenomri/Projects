#include <mpi.h>
#include <omp.h>
#include <stdio.h>
#include <stdlib.h>
#include "math.h"



#define MAX 10000
#define ROOT 0
double* createArrayFromFile(char* fileName, int* sizeOfElement );
double* createArray(int size);
double min(double Anumber );



void main(int argc, char** argv){

    int myrank;
    int processSize;
    int A_size;
    double* A;
    double* my_array_A ,*my_array_B;
    int my_array_size;
     double* array_Minmun;
     double start_time, end_time;


    MPI_Init(&argc, &argv);
    MPI_Comm_rank(MPI_COMM_WORLD, &myrank);
    MPI_Comm_size(MPI_COMM_WORLD, &processSize);


if(myrank == ROOT)
{
	// the root process read all the element from the file to array A.
	//after that the root calculation the amount per process by (A_size/processSize)
	//and finally the root create array of minimum to receive from process and to compare()
	// line(64) he gets the minimum from each process.
	//line(70) checkout the minimum element from array minumum

	  start_time= omp_get_wtime();
  A=createArrayFromFile("input.txt",&A_size);
  my_array_size= A_size/processSize;
  array_Minmun=createArray(processSize);
}

// the root sent bcast to process to tell him about the array size responsibility.
// every process create 2 array A and B
// and after that scatter every one gets her number from ROOT.
  my_array_size= A_size/processSize;
  MPI_Bcast(&processSize,1,MPI_INT,ROOT,MPI_COMM_WORLD);
  my_array_A=createArray(my_array_size);
  my_array_B=createArray(my_array_size);
  MPI_Scatter(A,my_array_size,MPI_DOUBLE,my_array_A,my_array_size,MPI_DOUBLE,ROOT,MPI_COMM_WORLD);



  //every process calculation the minimum  all of B[i], and after that the reduction choose only the the smaller minimum
double Minmum=1;
#pragma omp parallel for reduction (min: Minmum)
    for(int i=0 ; i < my_array_size; i++)
    {
        my_array_B[i]=min(my_array_A[i]);
        if(my_array_B[i] < Minmum)
            Minmum=my_array_B[i];
    }


  // here evey process sent to ROOT the her Minimum.
MPI_Gather(&Minmum,1,MPI_DOUBLE,array_Minmun,1,MPI_DOUBLE,ROOT,MPI_COMM_WORLD);

if(myrank == ROOT )
{
     double min=1;
     for (int i = 0; i < processSize; i++)
      {
        if(array_Minmun[i] < min)
            min = array_Minmun[i];
      }
}

MPI_Finalize();
if(myrank == ROOT )
{
	end_time= omp_get_wtime();
	printf("The Time is:%lf\n", end_time-start_time);
}


}


// calculation the minimum exasperation sin(j*exp(sin(Anumber * j)
//when we checkout witch index j gave us the minmum of exasperation
double min(double Anumber ){

double Minmum=1;
double temp;
    #pragma omp parallel for private(temp) reduction (min: Minmum)
        for(int j=0 ;  j< MAX ; j++)
        {
           temp= sin(j*exp(sin(Anumber * j)));

           if(temp<Minmum)
                Minmum=temp;
        }
          return Minmum;
    }

//Create array of double element by given size
double* createArray( int size)
{
    double* array = (double*)calloc(size,sizeof(double));
    return array;
}

//Reading from file
double* createArrayFromFile(char* fileName, int* sizeOfElement )
{
    FILE* f = fopen(fileName ,"r+");
    if(!f) return NULL;

    fscanf(f,"%d",sizeOfElement);
    double* array = (double*)calloc(*sizeOfElement,sizeof(double));
    if(!array ) return NULL;

    for(int i=0 ;i < *sizeOfElement ; i++)
        fscanf(f,"%lf",&array[i]);

      fclose(f);
    return array;

}
