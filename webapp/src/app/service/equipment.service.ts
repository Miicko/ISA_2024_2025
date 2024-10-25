import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Equipment } from '../model/equipment';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class EquipmentService {
  private headers: HttpHeaders;

  constructor(private http: HttpClient) {
    this.headers = new HttpHeaders({
      'Content-Type': 'application/json',
      'Accept': '*/*',
    })
   }

  public findForCompany(companyId : any): Observable<Equipment[]> {
    return this.http.get<Equipment[]>("http://localhost:8080/api/v1/companies/"+companyId+"/equipments", {headers:this.headers});
  }
  public createEquipment(companyId: any, req : Equipment) {
    return this.http.post<string>("http://localhost:8080/api/v1/companies/"+companyId+"/equipments", JSON.stringify(req), {headers:this.headers});
  }
  public deleteEquipment(req : Equipment) {
    return this.http.delete<string>("http://localhost:8080/api/v1/equipments/"+req.id, {headers:this.headers});
  }
  public updateEquipment(companyId: any, req : Equipment) {
    return this.http.put<any>("http://localhost:8080/api/v1/companies/"+ companyId +"/equipments/"+req.id, JSON.stringify(req), {headers:this.headers});
  }
}
