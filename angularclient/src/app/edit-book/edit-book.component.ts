import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { BookServiceService } from 'src/service/book-service.service';
import { Book } from '../book';

@Component({
  selector: 'app-edit-book',
  templateUrl: './edit-book.component.html',
  styleUrls: ['./edit-book.component.css']
})

export class EditBookComponent {

  book: Book;

  constructor(
    private route: ActivatedRoute, 
      private router: Router, 
        private bookService: BookServiceService) {
    this.book = new Book();

    this.route.params.subscribe(params => {
      this.book.price = params['price'];
      this.book.title = params['title'];
      this.book.author = params['author'];
      //get requests
    });

  }


  onSubmit() {
    this.bookService.edit(this.book).subscribe(result => this.gotoBookList());
  }

  gotoBookList() {
    this.router.navigate(['/books']);
  }


}
