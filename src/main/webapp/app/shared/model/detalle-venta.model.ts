import { IVenta } from 'app/shared/model/venta.model';

export interface IDetalleVenta {
  id?: number;
  productoNombre?: string;
  productoId?: number;
  ventas?: IVenta[];
}

export class DetalleVenta implements IDetalleVenta {
  constructor(public id?: number, public productoNombre?: string, public productoId?: number, public ventas?: IVenta[]) {}
}
