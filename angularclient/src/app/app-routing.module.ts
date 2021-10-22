import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { BookFormComponent } from './book-form/book-form.component';
import { BookListComponent } from './book-list/book-list.component';
import { EditBookComponent } from './edit-book/edit-book.component';

const routes: Routes = [
  { path: 'books', component: BookListComponent },
  { path: 'addbook', component: BookFormComponent },
  { path: 'editbook/:id/:title/:author/:price', component: EditBookComponent }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
