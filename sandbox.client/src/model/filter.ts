export class Filter {
  //sort:sort, ord: ord, filter: filter, like: like
  public sort: string;
  public ord: string;
  public filter: string;
  public like: string;

  constructor(sort="", ord="", filter="", like=""){
    this.sort = sort;
    this.ord = ord;
    this.filter = filter+"_like";
    this.like = like;
  }

  public toJson(a: any) {
    return {_sort: a.sort, _order: a.ord,  name_like: a.like};
  }
}
