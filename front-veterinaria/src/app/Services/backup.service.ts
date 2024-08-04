import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { API_URL } from '../Environments/routes';

@Injectable({
  providedIn: 'root'
})
export class BackupService {

  constructor(private http: HttpClient) { }

  downloadBackup(): Observable<any> {
    return this.http.get(API_URL.backup.download, { responseType: 'blob' });
  }
}
