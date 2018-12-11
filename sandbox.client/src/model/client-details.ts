import {Client} from "./client";
import {ClientAddr} from "./client-addr";
import {ClientPhone} from "./client-phone";

export class ClientDetails {
  public client: Client;
  public addrs: ClientAddr[];
  public phones: ClientPhone[];

}

