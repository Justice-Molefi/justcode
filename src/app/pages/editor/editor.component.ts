import { AfterViewInit, Component, ElementRef, HostListener, Input, OnInit } from '@angular/core';
import loader from '@monaco-editor/loader';
import * as monaco from 'monaco-editor'
import { SidebarComponent } from './sidebar/sidebar.component';
import { OutputComponent } from './output/output.component';
import { FontAwesomeModule } from '@fortawesome/angular-fontawesome';
import { faPlay, IconDefinition } from '@fortawesome/free-solid-svg-icons';
import { faJs } from '@fortawesome/free-brands-svg-icons';




@Component({
  selector: 'app-editor',
  standalone: true,
  imports: [SidebarComponent, OutputComponent, FontAwesomeModule],
  templateUrl: './editor.component.html',
  styleUrl: './editor.component.css'
})
export class EditorComponent implements OnInit, AfterViewInit{

  @Input() editorOptions: monaco.editor.IStandaloneEditorConstructionOptions | undefined;
  private editorInstance!: monaco.editor.IStandaloneCodeEditor; 

  editorHeight!: number;
  outputHeight : number = 100;
  actionBarHeight: number = 70;
  isResizing: boolean = false;
  faPlay: IconDefinition = faPlay;
  faJs: IconDefinition = faJs;



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

  ngAfterViewInit(): void {
    this.calculateEditorHeight();
  }

  @HostListener('document: mousemove', ['$event'])
  onMouseMove(event: MouseEvent): void{
    if(!this.isResizing) return;

    const containerHeight = window.innerHeight;
    const offsetY = event.clientY;

    this.outputHeight = containerHeight - offsetY;
    this.calculateEditorHeight();

    
    if(this.outputHeight > containerHeight - 250) {
      this.isResizing = false;
      this.outputHeight = this.outputHeight - 10;
    }

    if(this.outputHeight < 100){
      this.isResizing = false;
      this.outputHeight = 100;
    }

  }

  startResizing(event: MouseEvent) : void{
    this.isResizing = true;
    event.preventDefault();
  }

  @HostListener('document: mouseup')
  stopResizing(): void{
    this.isResizing = false;
  }

  calculateEditorHeight() : void{
    this.editorHeight = window.innerHeight - this.outputHeight - this.actionBarHeight - 4;
  }
}
