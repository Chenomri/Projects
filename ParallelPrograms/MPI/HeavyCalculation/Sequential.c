
#include <stdio.h>
#include <math.h>
#include <time.h>

#define HEAVY  1000
#define SIZE       40
#define RADIUS 10

// This function performs heavy computations, 
// its run time depends on x and y values
// DO NOT change the function
double heavy(int x, int y) {
	int i, loop;
	double sum = 0;

	if (sqrt((x - 0.75*SIZE)*(x - 0.75*SIZE) + (y - 0.25*SIZE)*(y - 0.25*SIZE)) < RADIUS)
		loop = 5*x*y;
	else
		loop = y + x;

	for (i = 0; i < loop*HEAVY; i++)
		sum += sin(exp(cos((double)i / HEAVY)));

	return  sum;
}

// Sequencial code to be parallelized
int main(int argc, char *argv[]) {
	int x, y;
	int size = SIZE;
	double answer = 0;

	__time_t t1 ,t2;
	t1=time(NULL) ;
	for (x = 0; x < size; x++){

		for (y = 0; y < size; y++) {
			answer += heavy(x, y);
        }
     
    }
	t2=time(NULL);
  
	printf("answer = %e\n", answer);
		printf("The time is: = %ld\n", t2-t1);


      }
