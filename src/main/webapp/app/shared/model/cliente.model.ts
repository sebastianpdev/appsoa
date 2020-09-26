export interface ICliente {
  id?: number;
  nombre?: string;
  apellido?: string;
  dni?: string;
  telefono?: string;
  email?: string;
}

export class Cliente implements ICliente {
  constructor(
    public id?: number,
    public nombre?: string,
    public apellido?: string,
    public dni?: string,
    public telefono?: string,
    public email?: string
  ) {}
}
