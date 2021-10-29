import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';


import { Book } from 'src/app/book';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class BookServiceService {

  private booksURL: string;

  constructor(private http: HttpClient) {
    this.booksURL = 'http://localhost:8080/books';
  }

  public findAll(): Observable<Book[]> {
    return this.http.get<Book[]>(this.booksURL);
  }

  public save(book: Book) {
    return this.http.post<Book>(this.booksURL, book);
  }

  public edit(book: Book) {
    return this.http.put<Book>(this.booksURL + "/" + book.id, book);
  }
}
