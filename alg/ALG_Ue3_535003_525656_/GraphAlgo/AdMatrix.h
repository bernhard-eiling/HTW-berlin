#pragma once

	using namespace System;
	using namespace System::ComponentModel;
	using namespace System::Collections;
	using namespace System::Windows::Forms;
	using namespace System::Data;
	using namespace System::Drawing;
	using namespace HTWGraphs;
	using namespace System::Collections::Generic;

ref class AdMatrix
{
public:
	AdMatrix(int vert);
	// sets the weights of the matrix
	void setWeight(int from, int to, int weight);
	// returns the weight at a given position
	int getWeight(int from, int to);
	// returns the size of one dimension of the matrix
	int getSize();
	int vert;
	
private:
	array<int, 2> ^matrix;
};

