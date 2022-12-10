#include <stdio.h>
#include <math.h>
#include "mpi.h"

#define HEAVY 1000
#define SIZE 40
#define RADIUS 10

// This function performs heavy computations,
// its run time depends on x and y values
// DO NOT change the function
double heavy(int x, int y)
{
	int i, loop;
	double sum = 0;

	if (sqrt((x - 0.75 * SIZE) * (x - 0.75 * SIZE) + (y - 0.25 * SIZE) * (y - 0.25 * SIZE)) < RADIUS)
		loop = 5 * x * y;
	else
		loop = y + x;

	for (i = 0; i < loop * HEAVY; i++)
		sum += sin(exp(cos((double)i / HEAVY)));

	return sum;
}

int main(int argc, char *argv[])
{
	int Xstart, Xstop, Ystart = 0, Ystop = SIZE;
	int size;
	int rank;
	int nprocs;
	double myanswer = 0;
	double t1, t2;

	MPI_Init(&argc, &argv);
	MPI_Comm_size(MPI_COMM_WORLD, &nprocs);
	MPI_Comm_rank(MPI_COMM_WORLD, &rank);

	size = SIZE / nprocs; // here we dont sent to every worker his/r range to work in order to time economy
	Xstart = size * rank; // the send message is more expensive then calculate that  calculation.
	Xstop = Xstart + size;

	MPI_Barrier(MPI_COMM_WORLD);
	t1 = MPI_Wtime();

	for (Xstart; Xstart < Xstop; Xstart++)
	{
		for (int i = 0; i < SIZE; i++)
		{
			myanswer += heavy(Xstart, i);
		}
	}

	if (rank != 0)
		MPI_Send(&myanswer, 1, MPI_DOUBLE, 0, 0, MPI_COMM_WORLD);
	else
	{
		double totalmyanswer = 0;
		double tempSum = 0;
		totalmyanswer = myanswer;
		for (int r = 1; r < nprocs; r++)
		{
			MPI_Recv(&tempSum, 1, MPI_DOUBLE, r, 0, MPI_COMM_WORLD, MPI_STATUS_IGNORE);
			totalmyanswer += tempSum;
		}

		t2 = MPI_Wtime();
		printf("The myanswer is:%e\n", totalmyanswer);
		printf("The myanswer is:%lf", t2 - t1);
	}

	MPI_Finalize();
}
