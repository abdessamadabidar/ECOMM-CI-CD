import { Component } from '@angular/core';
import {RouterLink, RouterOutlet} from '@angular/router';

const backend_host = process.env["BACKEND_HOST"] || 'localhost'
const backend_port = process.env["BACKEND_PORT"] || '8080'
@Component({
  selector: 'app-root',
  standalone: true,
  imports: [RouterOutlet, RouterLink],
  templateUrl: './app.component.html',
  styleUrl: './app.component.css'
})
export class AppComponent {
  title = 'ecomm-ci-cd';

    constructor() {
        console.log('===================== DEBUG ===================')
        console.log('Backend Host:', process.env["BACKEND_HOST"]);
        console.log('Backend Port:', process.env["BACKEND_PORT"]);
    }
}
