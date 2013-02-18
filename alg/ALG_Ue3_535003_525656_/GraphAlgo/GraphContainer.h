#pragma once
#include "AdMatrix.h"

	using namespace System;
	using namespace System::ComponentModel;
	using namespace System::Collections;
	using namespace System::Windows::Forms;
	using namespace System::Data;
	using namespace System::Drawing;
	using namespace HTWGraphs;
	using namespace System::Collections::Generic;

ref class GraphContainer
{
public:
	GraphContainer(List<GraphConnection ^> ^graphList);
	List<GraphConnection ^> ^graphNodes;
	ArrayList ^names;
	ArrayList ^fractAgive;
	ArrayList ^fractBgive;
	AdMatrix ^matrix;
	AdMatrix ^matrixNorm;
	String ^startString;
	String ^endString;

	// generates the shortetst path using the dijkstra algorithmus
	String ^shortestPath();
	String ^shortestPathTest();
	void getFractions();

	// sets the start and end node
	void setStartEnd(String ^startTemp, String ^endTemp);

	// returns boolean if the node is isolated
	bool ^isIsolated(String ^node);
	// returns an ArrayList of all nodes which are previous to the given node
	ArrayList ^getPrev(String ^node);
	// returns an ArrayList of all nodes which are next to the given node
	ArrayList ^getNext(String ^node);

private:

	// maps the node names to integers
	void mapNames();

	// generates the adjacency matrix for storing the weight of the graph
	void generateNormalMatrix();
	void generatePositivMatrix();
	

	// returns distance between two nodes
	int getNodeDist(int node1, int node2);
	ArrayList ^unsettledVert;
	ArrayList ^settledVert;
	ArrayList ^neighbourVert;
	array<int> ^dist;
	array<int> ^pred;
	Int32 ^integ;
	int compare;
	ArrayList ^next;
	ArrayList ^prev;
};

