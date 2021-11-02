import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { BookServiceService } from 'src/service/book-service.service';
import { Book } from '../book';


@Component({
  selector: 'app-edit-book',
  templateUrl: './book-edit.component.html',
  styleUrls: ['./book-edit.component.css']
})

export class BookEditComponent {

  book: Book;
  booksInSystem: Book[] = [];
  

  constructor(
    private route: ActivatedRoute, 
      private router: Router, 
        private bookService: BookServiceService) {
    this.book = new Book();

    this.route.params.subscribe(params => {
      this.book.id = params['id'];
      this.bookService.getById(this.book.id).subscribe(element => {
        this.book.title = element.title;
        this.book.author = element.author;
        this.book.price = element.price;
      })

    });

  }


  onSubmit() {
    this.bookService.edit(this.book).subscribe(result => this.gotoBookList());
  }

  gotoBookList() {
    this.router.navigate(['/books']);
  }


}