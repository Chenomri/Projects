
#include <stdlib.h>
#include <time.h>
#include <math.h>
#include "glut.h"
#include <vector>
#include "Cell.h"
#include <iostream>

using namespace std;

const int W = 500; // window Width
const int H = 500; // window Height
const int MSZ = 100;

const int SPACE = 0;
const int WALL = 1;
const int START = 2;
const int TARGET = 3;
const int BLACK = 4;
const int GRAY = 5;
const int PATH = 6;

int maze[MSZ][MSZ] = { 0 };
bool runBFS = false;

vector <Cell*> grays;

void InitMaze();

void init()
{
	srand(time(0));
	glClearColor(0, 0.3, 0.0, 0);// color of window background
	glOrtho(0, MSZ, 0, MSZ, -1, 1); // set the coordinates system

	InitMaze();
}

void InitMaze()
{
	int i, j;

	// the frame of maze is made of walls
	for (i = 0; i < MSZ; i++)
	{
		maze[0][i] = WALL;
		maze[MSZ - 1][i] = WALL;
		maze[i][0] = WALL;
		maze[i][MSZ - 1] = WALL;
	}
	for (i = 1; i < MSZ - 1; i++)
		for (j = 1; j < MSZ - 1; j++)
		{
			if (i % 2 == 0) // mostly SPACES
			{
				if (rand() % 100 < 80)
					maze[i][j] = SPACE;
				else
					maze[i][j] = WALL;
			}
			else //  mostly walls
			{
				if (rand() % 100 < 60)
					maze[i][j] = WALL;
				else
					maze[i][j] = SPACE;
			}
		}
	// set START and TARGET
	maze[MSZ / 2][MSZ / 2] = START;
	// add start Cell to grays
	Cell* pc = new Cell(MSZ / 2, MSZ / 2, nullptr);
	grays.push_back(pc);
	maze[rand() % MSZ][rand() % MSZ] = TARGET;
}

void DrawMaze()
{
	int i, j;

	for (i = 0; i < MSZ; i++)
		for (j = 0; j < MSZ; j++)
		{
			switch (maze[i][j])
			{
			case SPACE:
				glColor3d(0.9, 0.9, 0.9); // light gray
				break;
			case WALL:
				glColor3d(0.3, 0.3, 0.3); // dark gray
				break;
			case START:
				glColor3d(0.5, 0.5, 0.9); // blue
				break;
			case TARGET:
				glColor3d(1, 0, 0); // red
				break;
			case BLACK:
				glColor3d(0.7, 1, 0.7); // light green
				break;
			case GRAY:
				glColor3d(1, 0.3, 0); // orange
				break;
			case PATH:
				glColor3d(0.8, 0.5, 1); // purple
				break;
			}
			// draw square
			glBegin(GL_POLYGON);
			glVertex2d(j, i);
			glVertex2d(j + 1, i);
			glVertex2d(j + 1, i + 1);
			glVertex2d(j, i + 1);
			glEnd();
		}
}

void RestorePath(Cell* pc)
{
	while (pc->getParent() != nullptr)
	{
		maze[pc->getRow()][pc->getColumn()] = PATH;
		pc = pc->getParent();
	}
}

void CheckNeighbor(int row, int column, Cell* pcurrent)
{
	if (maze[row][column] == TARGET)
	{
		runBFS = false; // stop running BFS
		cout << "The solution has been found\n";
		RestorePath(pcurrent);
	}
	else // maze[row][column] is WHITE
	{
		Cell* pn = new Cell(row, column, pcurrent);
		// paint this neighbor gray
		grays.push_back(pn);
		maze[row][column] = GRAY;
	}
}

void RunBFSIteration()
{
	Cell* pcurrent;
	int r, c;
	if (grays.empty()) // no solution exists
	{
		runBFS = false; // stop running BFS
		cout << "No solution\n";
		return;
	}
	else // grays is not empty
	{
		pcurrent = *grays.begin();
		r = pcurrent->getRow();
		c = pcurrent->getColumn();
		maze[r][c] = BLACK; // paint it black
		grays.erase(grays.begin());
		// add all white neighbors of pcurrent to grays
		// UP
		if (maze[r + 1][c] == SPACE || maze[r + 1][c] == TARGET)
			CheckNeighbor(r + 1, c, pcurrent);
		// DOWN
		if (runBFS && (maze[r - 1][c] == SPACE || maze[r - 1][c] == TARGET))
			CheckNeighbor(r - 1, c, pcurrent);
		// LEFT
		if (runBFS && (maze[r][c - 1] == SPACE || maze[r][c - 1] == TARGET))
			CheckNeighbor(r, c - 1, pcurrent);
		// RIGHT
		if (runBFS && (maze[r][c + 1] == SPACE || maze[r][c + 1] == TARGET))
			CheckNeighbor(r, c + 1, pcurrent);

	}
}

void display()
{
	glClear(GL_COLOR_BUFFER_BIT); // clean frame buffer

	DrawMaze();

	glutSwapBuffers(); // show all
}

void idle()
{
	if (runBFS)
		RunBFSIteration();

	glutPostRedisplay(); // indirect call to display
}

void menu(int choice) {
	switch (choice) {
	case 1: // BFS
		runBFS = true;
		break;

	}
}

void main(int argc, char* argv[])
{
	glutInit(&argc, argv);
	glutInitDisplayMode(GLUT_RGB | GLUT_DOUBLE);
	glutInitWindowSize(W, H);
	glutInitWindowPosition(400, 50);
	glutCreateWindow("BFS");

	glutDisplayFunc(display); // display is the refresh function
	glutIdleFunc(idle);

	// menu
	glutCreateMenu(menu);
	glutAddMenuEntry("Run BFS", 1);
	glutAddMenuEntry("Run DFS", 2);
	glutAttachMenu(GLUT_RIGHT_BUTTON);

	init();

	glutMainLoop();
}