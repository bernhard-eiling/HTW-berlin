#pragma once

#include "GraphContainer.h"
#include "AdMatrix.h"


namespace GraphAlgo {

	using namespace System;
	using namespace System::ComponentModel;
	using namespace System::Collections;
	using namespace System::Windows::Forms;
	using namespace System::Data;
	using namespace System::Drawing;
	using namespace HTWGraphs;
	using namespace System::Collections::Generic;

	/// <summary>
	/// Zusammenfassung für Form1
	///
	/// Warnung: Wenn Sie den Namen dieser Klasse ändern, müssen Sie auch
	///          die Ressourcendateiname-Eigenschaft für das Tool zur Kompilierung verwalteter Ressourcen ändern,
	///          das allen RESX-Dateien zugewiesen ist, von denen diese Klasse abhängt.
	///          Anderenfalls können die Designer nicht korrekt mit den lokalisierten Ressourcen
	///          arbeiten, die diesem Formular zugewiesen sind.
	/// </summary>
	public ref class Form1 : public System::Windows::Forms::Form
	{
	public:
		Form1(void)
		{
			InitializeComponent();
			//
			//TODO: Konstruktorcode hier hinzufügen.
			//
		}

	protected:
		/// <summary>
		/// Verwendete Ressourcen bereinigen.
		/// </summary>
		~Form1()
		{
			if (components)
			{
				delete components;
			}
		}
	private: System::Windows::Forms::Button^  button1;
	private: System::Windows::Forms::ComboBox^  comboBoxStart;
	private: System::Windows::Forms::TextBox^  textBoxShort;

	private: System::Windows::Forms::GroupBox^  groupBox1;
	private: System::Windows::Forms::ComboBox^  comboBoxEnd;
	private: System::Windows::Forms::Button^  button2;
			 GraphContainer ^graph;
			 String ^out;
			 String ^tabi;
	private: System::Windows::Forms::ListBox^  listBox1;
	private: System::Windows::Forms::Label^  label1;
	private: System::Windows::Forms::Label^  label2;
	private: System::Windows::Forms::OpenFileDialog^  openFileDia;
	private: System::Windows::Forms::GroupBox^  groupBox2;
	private: System::Windows::Forms::GroupBox^  groupBox3;
	private: System::Windows::Forms::GroupBox^  groupBox4;
	private: System::Windows::Forms::ListBox^  listBox2;
	private: System::Windows::Forms::GroupBox^  groupBox5;
	private: System::Windows::Forms::ListBox^  listBox3;


	protected: 

	private:
		/// <summary>
		/// Erforderliche Designervariable.
		/// </summary>
		System::ComponentModel::Container ^components;

#pragma region Windows Form Designer generated code
		/// <summary>
		/// Erforderliche Methode für die Designerunterstützung.
		/// Der Inhalt der Methode darf nicht mit dem Code-Editor geändert werden.
		/// </summary>
		void InitializeComponent(void)
		{
			this->button1 = (gcnew System::Windows::Forms::Button());
			this->comboBoxStart = (gcnew System::Windows::Forms::ComboBox());
			this->textBoxShort = (gcnew System::Windows::Forms::TextBox());
			this->groupBox1 = (gcnew System::Windows::Forms::GroupBox());
			this->comboBoxEnd = (gcnew System::Windows::Forms::ComboBox());
			this->button2 = (gcnew System::Windows::Forms::Button());
			this->listBox1 = (gcnew System::Windows::Forms::ListBox());
			this->label1 = (gcnew System::Windows::Forms::Label());
			this->label2 = (gcnew System::Windows::Forms::Label());
			this->openFileDia = (gcnew System::Windows::Forms::OpenFileDialog());
			this->groupBox2 = (gcnew System::Windows::Forms::GroupBox());
			this->groupBox3 = (gcnew System::Windows::Forms::GroupBox());
			this->groupBox4 = (gcnew System::Windows::Forms::GroupBox());
			this->listBox2 = (gcnew System::Windows::Forms::ListBox());
			this->groupBox5 = (gcnew System::Windows::Forms::GroupBox());
			this->listBox3 = (gcnew System::Windows::Forms::ListBox());
			this->groupBox1->SuspendLayout();
			this->groupBox2->SuspendLayout();
			this->groupBox3->SuspendLayout();
			this->groupBox4->SuspendLayout();
			this->groupBox5->SuspendLayout();
			this->SuspendLayout();
			// 
			// button1
			// 
			this->button1->Location = System::Drawing::Point(328, 12);
			this->button1->Name = L"button1";
			this->button1->Size = System::Drawing::Size(149, 35);
			this->button1->TabIndex = 0;
			this->button1->Text = L"Load Graph";
			this->button1->UseVisualStyleBackColor = true;
			this->button1->Click += gcnew System::EventHandler(this, &Form1::button1_Click);
			// 
			// comboBoxStart
			// 
			this->comboBoxStart->FormattingEnabled = true;
			this->comboBoxStart->Location = System::Drawing::Point(6, 29);
			this->comboBoxStart->Name = L"comboBoxStart";
			this->comboBoxStart->Size = System::Drawing::Size(149, 21);
			this->comboBoxStart->TabIndex = 1;
			// 
			// textBoxShort
			// 
			this->textBoxShort->Location = System::Drawing::Point(6, 19);
			this->textBoxShort->Name = L"textBoxShort";
			this->textBoxShort->Size = System::Drawing::Size(749, 20);
			this->textBoxShort->TabIndex = 2;
			// 
			// groupBox1
			// 
			this->groupBox1->Controls->Add(this->textBoxShort);
			this->groupBox1->Location = System::Drawing::Point(9, 657);
			this->groupBox1->Name = L"groupBox1";
			this->groupBox1->Size = System::Drawing::Size(761, 54);
			this->groupBox1->TabIndex = 3;
			this->groupBox1->TabStop = false;
			this->groupBox1->Text = L"Shortest Path";
			// 
			// comboBoxEnd
			// 
			this->comboBoxEnd->FormattingEnabled = true;
			this->comboBoxEnd->Location = System::Drawing::Point(6, 29);
			this->comboBoxEnd->Name = L"comboBoxEnd";
			this->comboBoxEnd->Size = System::Drawing::Size(149, 21);
			this->comboBoxEnd->TabIndex = 4;
			// 
			// button2
			// 
			this->button2->Location = System::Drawing::Point(327, 601);
			this->button2->Name = L"button2";
			this->button2->Size = System::Drawing::Size(150, 40);
			this->button2->TabIndex = 5;
			this->button2->Text = L"Generate Shortest Path";
			this->button2->UseVisualStyleBackColor = true;
			this->button2->Click += gcnew System::EventHandler(this, &Form1::button2_Click);
			// 
			// listBox1
			// 
			this->listBox1->FormattingEnabled = true;
			this->listBox1->Location = System::Drawing::Point(195, 63);
			this->listBox1->Name = L"listBox1";
			this->listBox1->Size = System::Drawing::Size(422, 329);
			this->listBox1->TabIndex = 6;
			this->listBox1->Click += gcnew System::EventHandler(this, &Form1::listBox1_Click);
			// 
			// label1
			// 
			this->label1->AutoSize = true;
			this->label1->Location = System::Drawing::Point(198, 412);
			this->label1->Name = L"label1";
			this->label1->Size = System::Drawing::Size(35, 13);
			this->label1->TabIndex = 7;
			this->label1->Text = L"label1";
			this->label1->Click += gcnew System::EventHandler(this, &Form1::label1_Click);
			// 
			// label2
			// 
			this->label2->AutoSize = true;
			this->label2->Location = System::Drawing::Point(198, 439);
			this->label2->Name = L"label2";
			this->label2->Size = System::Drawing::Size(35, 13);
			this->label2->TabIndex = 8;
			this->label2->Text = L"label2";
			this->label2->Click += gcnew System::EventHandler(this, &Form1::label2_Click);
			// 
			// openFileDia
			// 
			this->openFileDia->FileName = L"openFile";
			// 
			// groupBox2
			// 
			this->groupBox2->Controls->Add(this->comboBoxStart);
			this->groupBox2->Location = System::Drawing::Point(195, 514);
			this->groupBox2->Name = L"groupBox2";
			this->groupBox2->Size = System::Drawing::Size(167, 73);
			this->groupBox2->TabIndex = 9;
			this->groupBox2->TabStop = false;
			this->groupBox2->Text = L"Start";
			this->groupBox2->Enter += gcnew System::EventHandler(this, &Form1::groupBox2_Enter_1);
			// 
			// groupBox3
			// 
			this->groupBox3->Controls->Add(this->comboBoxEnd);
			this->groupBox3->Location = System::Drawing::Point(451, 514);
			this->groupBox3->Name = L"groupBox3";
			this->groupBox3->Size = System::Drawing::Size(166, 73);
			this->groupBox3->TabIndex = 10;
			this->groupBox3->TabStop = false;
			this->groupBox3->Text = L"End";
			this->groupBox3->Enter += gcnew System::EventHandler(this, &Form1::groupBox3_Enter);
			// 
			// groupBox4
			// 
			this->groupBox4->Controls->Add(this->listBox2);
			this->groupBox4->Location = System::Drawing::Point(9, 412);
			this->groupBox4->Name = L"groupBox4";
			this->groupBox4->Size = System::Drawing::Size(180, 239);
			this->groupBox4->TabIndex = 0;
			this->groupBox4->TabStop = false;
			this->groupBox4->Text = L"Fraction_A_";
			// 
			// listBox2
			// 
			this->listBox2->FormattingEnabled = true;
			this->listBox2->Location = System::Drawing::Point(8, 17);
			this->listBox2->Name = L"listBox2";
			this->listBox2->Size = System::Drawing::Size(158, 212);
			this->listBox2->TabIndex = 0;
			this->listBox2->SelectedIndexChanged += gcnew System::EventHandler(this, &Form1::listBox2_SelectedIndexChanged);
			// 
			// groupBox5
			// 
			this->groupBox5->Controls->Add(this->listBox3);
			this->groupBox5->Location = System::Drawing::Point(623, 411);
			this->groupBox5->Name = L"groupBox5";
			this->groupBox5->Size = System::Drawing::Size(170, 240);
			this->groupBox5->TabIndex = 11;
			this->groupBox5->TabStop = false;
			this->groupBox5->Text = L"Fraction_B_";
			this->groupBox5->Enter += gcnew System::EventHandler(this, &Form1::groupBox5_Enter);
			// 
			// listBox3
			// 
			this->listBox3->FormattingEnabled = true;
			this->listBox3->Location = System::Drawing::Point(9, 20);
			this->listBox3->Name = L"listBox3";
			this->listBox3->Size = System::Drawing::Size(147, 212);
			this->listBox3->TabIndex = 0;
			// 
			// Form1
			// 
			this->AutoScaleDimensions = System::Drawing::SizeF(6, 13);
			this->AutoScaleMode = System::Windows::Forms::AutoScaleMode::Font;
			this->ClientSize = System::Drawing::Size(805, 735);
			this->Controls->Add(this->groupBox5);
			this->Controls->Add(this->groupBox2);
			this->Controls->Add(this->groupBox4);
			this->Controls->Add(this->groupBox3);
			this->Controls->Add(this->label2);
			this->Controls->Add(this->label1);
			this->Controls->Add(this->listBox1);
			this->Controls->Add(this->button2);
			this->Controls->Add(this->groupBox1);
			this->Controls->Add(this->button1);
			this->Name = L"Form1";
			this->Text = L"HTW Algorithmen - Sample Form";
			this->Load += gcnew System::EventHandler(this, &Form1::Form1_Load);
			this->groupBox1->ResumeLayout(false);
			this->groupBox1->PerformLayout();
			this->groupBox2->ResumeLayout(false);
			this->groupBox3->ResumeLayout(false);
			this->groupBox4->ResumeLayout(false);
			this->groupBox5->ResumeLayout(false);
			this->ResumeLayout(false);
			this->PerformLayout();

		}
#pragma endregion
	private: System::Void button1_Click(System::Object^  sender, System::EventArgs^  e) {
				 GraphLoader l;
				 ArrayList testo;
				 String^ fileString = "";
				 OpenFileDialog^ openGraph = gcnew OpenFileDialog;
				 openGraph->InitialDirectory = "C:\\";

				 if ( openGraph->ShowDialog() == System::Windows::Forms::DialogResult::OK )
				 {
					 
				 fileString = openGraph->FileName;

				 List<GraphConnection ^> ^graphNodes = gcnew List<GraphConnection ^>();

				 graphNodes = l.load(fileString);
				 graph = gcnew GraphContainer(graphNodes);
				
				  
				 tabi = "\t";

				  bool ^spezi = false;
				  String ^isNext = "";
				  String ^isPrev = "";

				  for( int i=0; i<graph->names->Count; i++ )
				 {
					
					 comboBoxStart->Items->Add(graph->names[i]);
					 comboBoxEnd->Items->Add(graph->names[i]);
					 if(graph->getNext(graph->names[i]->ToString())->Count == 0) {
						 isNext = "Ausgangsgrad gleich 0";
					} else {
						isNext = "";
					}

					 if(graph->getPrev(graph->names[i]->ToString())->Count == 0) {
						 isPrev = "Eingangsgrad gleich 0";
					} else {
						isPrev = "";
					}


					spezi = graph->isIsolated(graph->names[i]->ToString());

					if(spezi->Equals(true))
					{
						listBox1->Items->Add(graph->names[i] + tabi + tabi  + tabi + isNext + tabi + isPrev + tabi +"IsSolated");
					}
					else
					{
						listBox1->Items->Add(graph->names[i] + tabi + tabi + tabi + isNext + tabi + isPrev);
					}
				 }
				  int sel = 0;
				  comboBoxStart->SelectedIndex = sel;
				  comboBoxEnd->SelectedIndex = sel+1;

				  listBox1->SelectedIndex = 0;				  
				  label1->Text = "Vorgänger:";
				  label2->Text = "Nachfolger:";
				} 
			 
				 for( int i=0; i<graph->fractAgive->Count; i++ )
				 {

				listBox2->Items->Add(graph->names[(int)graph->fractAgive[i]]);
				
				 }
				 for( int i=0; i<graph->fractAgive->Count; i++ )
				 {
				listBox3->Items->Add(graph->names[(int)graph->fractBgive[i]]);
				
				 }

			 }

private: System::Void button2_Click(System::Object^  sender, System::EventArgs^  e) {
			
			graph->setStartEnd((String^)comboBoxStart->SelectedItem, (String^)comboBoxEnd->SelectedItem);
			out = graph->shortestPath();
			textBoxShort->Text = out;

			
		 }
private: System::Void listbox1_SelectedIndexChanged(System::Object^ sender, System::EventArgs^ e) {
			label1->Text = "Vorgänger:"+"Here werden die Vorgänger aufgezählt von: "+graph->names[listBox1->SelectedIndex];
		    label2->Text = "Nachfolger:"+"Here werden die Nachfolger aufgezählt von: "+graph->names[listBox1->SelectedIndex];
		 }
private: System::Void label1_Click(System::Object^  sender, System::EventArgs^  e) {
			 MessageBox::Show( String::Format("don't be so clicky") );
		 }
private: System::Void label2_Click(System::Object^  sender, System::EventArgs^  e) {
			MessageBox::Show( String::Format("don't be so clicky") );
		 }
private: System::Void Form1_Load(System::Object^  sender, System::EventArgs^  e) {
		 }

private: System::Void groupBox2_Enter_1(System::Object^  sender, System::EventArgs^  e) {
		 }
private: System::Void groupBox3_Enter(System::Object^  sender, System::EventArgs^  e) {
		 }
private: System::Void listBox1_Click(System::Object^  sender, System::EventArgs^  e) {
			 
			 int tempInd = listBox1->SelectedIndex;
			 String ^clicked = graph->names[tempInd]->ToString();

			 String^ vori = "";
			String^ nachi = "";
			ArrayList^ prev = gcnew ArrayList;
			ArrayList^ next = gcnew ArrayList;

			prev = graph->getPrev(clicked);
			for (int i=0; i<prev->Count;i++)
			{
				vori +=(dynamic_cast<String^>(prev[i]))+", ";
			}

			next = graph->getNext(clicked);

			for (int i=0; i<next->Count;i++)
			{
				
				nachi +=(dynamic_cast<String^>(next[i]))+", ";
		 }
			label1->Text = "Vorgänger von " + clicked + ": " + vori;
			label2->Text ="Nachfolger von "+ clicked + ": " + nachi;
		 }
private: System::Void listBox2_SelectedIndexChanged(System::Object^  sender, System::EventArgs^  e) {
		 }
private: System::Void groupBox5_Enter(System::Object^  sender, System::EventArgs^  e) {
		 }
};
}

