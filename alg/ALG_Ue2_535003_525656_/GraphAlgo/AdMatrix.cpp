#include "StdAfx.h"
#include "AdMatrix.h"

	using namespace System;
	using namespace System::ComponentModel;
	using namespace System::Collections;
	using namespace System::Windows::Forms;
	using namespace System::Data;
	using namespace System::Drawing;
	using namespace HTWGraphs;
	using namespace System::Collections::Generic;

AdMatrix::AdMatrix(int vertTemp)
{
	vert = vertTemp;
	matrix = gcnew array<int, 2>(vert, vert);
}

void AdMatrix::setWeight(int from, int to, int weight) {
	matrix[from,to] = weight;
}

int AdMatrix::getWeight(int from, int to) {
	return matrix[from,to];
}

int AdMatrix::getSize() {
	return vert;
}