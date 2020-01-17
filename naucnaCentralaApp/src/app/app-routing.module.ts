import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { RegisterComponent } from './register/register.component';
import { UnosOblastiComponent } from './unos-oblasti/unos-oblasti.component';
import { LoginComponent } from './login/login.component';
import { AdminComponent } from './admin/admin.component';
import { HomeComponent } from './home/home.component';
import { EditorComponent } from './editor/editor.component';
import { AddEditorsComponent } from './add-editors/add-editors.component';
import { CheckDataComponent } from './check-data/check-data.component';

const routes: Routes = [

  {path: '', component: HomeComponent, pathMatch: 'full'}, 
  {path: 'register/:mode', component: RegisterComponent},
  {path: 'unosOblasti', component: UnosOblastiComponent},
  {path: 'unosOblasti/:mode', component: UnosOblastiComponent},
  {path: 'login', component: LoginComponent},
  {path: 'admin', component: AdminComponent},
  {path: 'editor', component: EditorComponent},
  {path: 'editor/:mode', component: EditorComponent},
  {path: 'admin', component: AdminComponent},
  {path: 'checkData', component: CheckDataComponent},
  
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
