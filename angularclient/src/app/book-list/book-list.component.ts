import { Component, OnInit } from '@angular/core';
import { BookServiceService } from 'src/service/book-service.service';
import { Book } from '../book';

@Component({
  selector: 'app-book-list',
  templateUrl: './book-list.component.html',
  styleUrls: ['./book-list.component.css']
})
export class BookListComponent implements OnInit {

  books: Book[] | undefined;

  constructor(private bookService: BookServiceService) {
  }

  ngOnInit() {
    this.bookService.findAll().subscribe(data => {
      this.books = data;
    });
  }

}
