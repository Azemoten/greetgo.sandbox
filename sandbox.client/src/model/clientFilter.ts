export class ClientFilter {
  public name: string;
  public surname: string;
  public patronymic: string;
  public sort: string;
  public order: boolean;
  public page: number;

  toJson(a: any){
    return {
      name: a.name,
      surname: a.surname,
      patronymic: a.patronymic,
      sort: a.sort,
      order: a.order,
      page: a.page
    }
  }
}
