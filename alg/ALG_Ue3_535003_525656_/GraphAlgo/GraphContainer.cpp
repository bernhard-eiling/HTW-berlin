#include "StdAfx.h"
#include "AdMatrix.h"
#include "GraphContainer.h"
#include <math.h>

	using namespace System;
	using namespace System::ComponentModel;
	using namespace System::Collections;
	using namespace System::Windows::Forms;
	using namespace System::Data;
	using namespace System::Drawing;
	using namespace HTWGraphs;
	using namespace System::Collections::Generic;


GraphContainer::GraphContainer(List<GraphConnection ^> ^graphList)
{
	graphNodes = graphList;
	integ = gcnew Int32();
	names = gcnew ArrayList;
	fractAgive = gcnew ArrayList;
	fractBgive = gcnew ArrayList;
	this->mapNames();
	this->generatePositivMatrix();
	this->generateNormalMatrix();
	this->getFractions();
}

void GraphContainer::getFractions() {
	int start = 0;
	ArrayList ^fractA = gcnew ArrayList();
	ArrayList ^fractB = gcnew ArrayList();
	// setting start
	fractA->Add(start);

	// loop over every column of matrix
	for(int i = 0; i < matrixNorm->getSize(); i++) {
		// loop over nodes of onde fraction
		for(int t = 0; t < fractA -> Count; t++) {
			// loop through one column
			for(int m = 0; m < matrixNorm->getSize(); m++) {
				// if weight over 0 nodes are in one fraction
				int weight = matrixNorm->getWeight((int)fractA[t], m);
				if(weight >= 0 && !fractA -> Contains(m)) {
					fractA->Add(m);
				}
			}
		}
	}

	// generating fraction B
	for(int i = 0; i < names -> Count; i++) {
		if(!fractA -> Contains(i)) {
			fractB -> Add(i);
		}
	}
	
	String ^text = "Fraction A: ";
	for(int i = 0; i < fractA->Count; i++) {
		text += names[(int)fractA[i]]+ ", ";
		fractAgive->Add(fractA[i]);

	}
	text += "Fraction B: ";
	for(int i = 0; i < fractB->Count; i++) {
		text += names[(int)fractB[i]] + ", ";
		fractBgive->Add(fractB[i]);
	}
	
	// hier kommen die Fraktion raus. 
	//MessageBox::Show( String::Format(text) );
	//String ^printFractions = "test";
	//---test_funker
	//if(frac != "zwei"){
		//printFractions = " nicht die 2";
		
	//}else{
		//printFractions ="zwei";

	//}

	//return printFractions;
}

//--test


String ^GraphContainer::shortestPathTest() {
	
	
	//Printing Path
	String ^print = "";
	print = "FunkerTest";
	
	return print;
}


//---testEnde

void GraphContainer::setStartEnd(String ^startTemp, String ^endTemp) {
	startString = startTemp;
	endString = endTemp;
}

// Mapping names of nodes to int
void GraphContainer::mapNames() {
	for( int i=0; i<graphNodes->Count; i++ ) {
		if(!names->Contains(graphNodes[i]->From)) {
			names->Add(graphNodes[i]->From);
		}
		if(!names->Contains(graphNodes[i]->To)) {
			names->Add(graphNodes[i]->To);
		}
	}
}

// generating and filling matrix with weights
void GraphContainer::generatePositivMatrix() {
	
	//filling matrix with default weight of -1 == no connection
	matrix = gcnew AdMatrix(names->Count);
	for(int i = 0; i < matrix->getSize(); i++) {
		for(int j = 0; j < matrix->getSize(); j++) {
			matrix->setWeight(i, j, -1);
		}
	}

	// getting lowest weight of matrix
	compare = integ->MaxValue;
	for( int i=0; i<graphNodes->Count; i++ ) {
		if(compare > graphNodes[i]->Weight) {
			compare = (int)graphNodes[i]->Weight;
		}
	}

	// compute absolut of compare
	double compareD = (double)compare;
	compareD = fabs(compareD);
	compare = (int)compareD;

	// filling matrix with adjusted weights (because of dijkstra fail with negative weights)
	for( int i=0; i<graphNodes->Count; i++ ) {
		int from = names->IndexOf(graphNodes[i]->From);
		int to = names->IndexOf(graphNodes[i]->To);
		int weight = (int)graphNodes[i]->Weight;
		matrix->setWeight(from, to, weight + compare);
	}
}

void GraphContainer::generateNormalMatrix() {
	
	//filling matrix with default weight of -integ->MaxValue == no connection
	matrixNorm = gcnew AdMatrix(names->Count);
	for(int i = 0; i < matrixNorm->getSize(); i++) {
		for(int j = 0; j < matrix->getSize(); j++) {
			matrixNorm->setWeight(i, j, -integ->MaxValue);
		}
	}

	// filling matrix with adjusted weights (because of dijkstra fail with negative weights)
	for( int i=0; i<graphNodes->Count; i++ ) {
		int from = names->IndexOf(graphNodes[i]->From);
		int to = names->IndexOf(graphNodes[i]->To);
		int weight = (int)graphNodes[i]->Weight;
		matrixNorm->setWeight(from, to, weight);
	}
}


String ^GraphContainer::shortestPath() {
	
	unsettledVert = gcnew ArrayList();
	settledVert = gcnew ArrayList();
	neighbourVert = gcnew ArrayList();
	pred = gcnew array<int>(names->Count);
	dist = gcnew array<int>(names->Count);

	// setting dist and pred to default values
	for(int i = 0; i < names->Count; i++) {
		pred[i] = -1;
		dist[i] = integ->MaxValue;
	}

	int start = names->IndexOf(startString);
	int end = names->IndexOf(endString);

	// setting dist of start and adding to unsettlerdVert
	dist[start] = 0;
	unsettledVert->Add(start);

	while(unsettledVert->Count > 0) {

		// Get vertex with minimum distance and delete it from unsettled
		unsettledVert->Sort();
		int u = (int)unsettledVert[0];
		unsettledVert->RemoveAt(0);
		settledVert->Add(u);

		// gather vertices connected to u and vertices not settled
		for(int i = 0; i < names->Count; i++) {
			if(matrix->getWeight(u, i) != -1 && !settledVert->Contains(i)) {
				neighbourVert->Add(i);
			}
		}

		for(int i = 0; i < neighbourVert->Count; i++) {

			// getting delta of u and neighbour
			int delta;
			delta = matrix->getWeight(u, (int)neighbourVert[i]);

			// looking for shorter path and updating dist and pred
			if(dist[(int)neighbourVert[i]] > dist[u] + delta) {
				dist[(int)neighbourVert[i]] = dist[u] + delta;
				pred[(int)neighbourVert[i]] = u;
				unsettledVert->Add(neighbourVert[i]);
			}
		}
		neighbourVert->Clear();
	}

	//Printing Path
	String ^print = "";
	if(dist[end] ==  integ->MaxValue) {
		print += "There is no connected path between start and end node.";
	} else {
		
		// print nodes on path
		int nextVert = end;
		int counter = 0;
		while(pred[nextVert] != -1) {
			counter++;
			print += " " + names[nextVert] + " ";
			int nodeDist = getNodeDist(nextVert, pred[nextVert]) - compare;
			print += " <-- " + nodeDist + " --> ";
			nextVert = pred[nextVert];
		}
		print += " " + names[nextVert] + " ";

		//print total weight of path
		int sumPath = dist[end] - (counter * compare);
		print += "  The path is " + sumPath + " units long.";
	}
	return print;
}

int GraphContainer::getNodeDist(int node1, int node2) {
	int returnDist;
	returnDist = matrix->getWeight(node1, node2);
	return returnDist;
}

bool ^GraphContainer::isIsolated(String ^nod) {
	ArrayList ^noNext = this->getNext(nod);
	ArrayList ^noPrev = this->getPrev(nod);

	if(noNext->Count <= 0 && noPrev->Count <= 0) {
		return true;
	} else {
		return false;
	}
}

ArrayList ^GraphContainer::getNext(String ^node) {
	next = gcnew ArrayList();
	int nodeTemp = names->IndexOf(node);

	// getting nodes which are NEXT of nodeTemp
	for(int j = 0; j < matrix->getSize(); j++) {
		if(matrix->getWeight(nodeTemp, j) != -1) {
			next->Add(names[j]);
		}
	}
	return next;
}

ArrayList ^GraphContainer::getPrev(String ^node) {
	prev = gcnew ArrayList();
	int nodeTemp = names->IndexOf(node);

	// getting nodes which are PREV of nodeTemp
	for(int j = 0; j < matrix->getSize(); j++) {
		if(matrix->getWeight(j, nodeTemp) != -1) {
			prev->Add(names[j]);
		}
	}
	return prev;
}