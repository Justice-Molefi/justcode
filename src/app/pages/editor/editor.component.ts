import { Component, ElementRef, Input, OnInit } from '@angular/core';
import loader from '@monaco-editor/loader';
import * as monaco from 'monaco-editor'
import { SidebarComponent } from './sidebar/sidebar.component';

@Component({
  selector: 'app-editor',
  standalone: true,
  imports: [SidebarComponent],
  templateUrl: './editor.component.html',
  styleUrl: './editor.component.css'
})
export class EditorComponent implements OnInit{

  @Input() editorOptions: monaco.editor.IStandaloneEditorConstructionOptions | undefined;
  private editorInstance!: monaco.editor.IStandaloneCodeEditor; 



  constructor(private el: ElementRef){
  }

  ngOnInit(){
    loader.init().then(monacoInstance => {
      this.editorInstance = monacoInstance.editor.create(this.el.nativeElement.querySelector('.editor'),{
        language: 'javascript',
        theme: 'vs-dark',
        fontSize: 20,
        automaticLayout: true,
        ...this.editorOptions
      })
    })
  }
}
