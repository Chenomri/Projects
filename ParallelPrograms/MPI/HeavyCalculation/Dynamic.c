#include <mpi.h>
#include <stdlib.h>
#include <stdio.h>
#include <stddef.h>
#include <time.h>
#include <string.h>
#include <math.h>

#define HEAVY 1000
#define SIZE 40
#define RADIUS 10
#define WORK_PER_WORKER 4 // can change under certain constraints.
typedef enum
{
    TO_FINISHE,
    TO_WORK,
    DONE
} State;
enum
{
    MASTER,
    WORKER
};

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

double doTheCalaoulation(int x, int x_plus_range)
{
    double sum = 0;
    for (x; x < x_plus_range; x++)
    {

        for (int y = 0; y < SIZE; y++)
        {

            sum += heavy(x, y);
        }
    }
    return sum;
}

void worker(int rank)
{

    MPI_Status status;
    State state;
    int range_X;

    do
    {
        MPI_Recv(&range_X, 1, MPI_INT, MASTER, MPI_ANY_TAG, MPI_COMM_WORLD, &status);
        state = status.MPI_TAG;

        if (state == TO_WORK)
        {

            double sum = doTheCalaoulation(range_X, range_X + WORK_PER_WORKER);

            MPI_Send(&sum, 1, MPI_DOUBLE, MASTER, DONE, MPI_COMM_WORLD);
        }
    } while (TO_FINISHE != status.MPI_TAG);
}

void master(int num_procs)
{

    double t1, t2;
    t1 = MPI_Wtime(); // Start measuring time

    State state;
    int range_X = 0;
    int tasks_sent = 0;
    int total_task = SIZE / WORK_PER_WORKER;

    // First we send to every worker one single assignment by transfer (x,x+range)
    for (int worker_rank = 1; worker_rank < num_procs; worker_rank++)
    {

        MPI_Send(&range_X, 1, MPI_INT, worker_rank, TO_WORK, MPI_COMM_WORLD);

        range_X += WORK_PER_WORKER;
        tasks_sent++;
    }

    int tasks_done = 0;
    double totalsum = 0;

    while (tasks_done < total_task)
    {
        MPI_Status status;
        double workerSum;

        // Earlier we receive from random worker because above we send to worker(line 82).
        MPI_Recv(&workerSum, 1, MPI_DOUBLE, MPI_ANY_SOURCE, DONE, MPI_COMM_WORLD, &status);
        totalsum += workerSum;
        tasks_done++;

        // after we recieived from specific worker we check if we have more work for his/r.
        //  if so we send more task(line 107).
        //  else we just sent last message to finish his/r action(line 113).
        if (tasks_sent < total_task)
        {

            MPI_Send(&range_X, 1, MPI_INT, status.MPI_SOURCE, TO_WORK, MPI_COMM_WORLD);
            range_X += WORK_PER_WORKER;
            tasks_sent++;
        }
        else
        {
            int dummy;
            MPI_Send(&dummy, 0, MPI_DOUBLE, status.MPI_SOURCE, TO_FINISHE, MPI_COMM_WORLD);
        }
    }

    t2 = MPI_Wtime();
    printf("the sum is %e\n", totalsum);
    printf("the time is %lf\n", t2 - t1);
}

int main(int argc, char *argv[])
{
    MPI_Init(&argc, &argv);
    int rank;
    int num_procs;

    MPI_Comm_size(MPI_COMM_WORLD, &num_procs);
    MPI_Comm_rank(MPI_COMM_WORLD, &rank);
    MPI_Barrier(MPI_COMM_WORLD);

    if (rank == MASTER)
        master(num_procs);
    else
        worker(rank);

    MPI_Finalize();
}