import {AddressType} from "./address.enum";

export class ClientAddr {
  public client: number;
  public type: AddressType;
  public street: string;
  public house: string;
  public flat: string;
}
