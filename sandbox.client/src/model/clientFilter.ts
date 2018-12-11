export class ClientFilter {
  public name: string;
  public surname: string;
  public patronymic: string;
  public sort: string;
  public order: string;
  public page: number;


  public toJson(a: any) {
    return {sort: a.sort, order: a.ord, name: a.name, surname: a.surname, patronymic: a.patronymic,
    page: a.page};
  }
}
