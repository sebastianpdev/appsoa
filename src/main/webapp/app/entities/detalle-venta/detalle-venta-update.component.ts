import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IDetalleVenta, DetalleVenta } from 'app/shared/model/detalle-venta.model';
import { DetalleVentaService } from './detalle-venta.service';
import { IProducto } from 'app/shared/model/producto.model';
import { ProductoService } from 'app/entities/producto/producto.service';

@Component({
  selector: 'jhi-detalle-venta-update',
  templateUrl: './detalle-venta-update.component.html',
})
export class DetalleVentaUpdateComponent implements OnInit {
  isSaving = false;
  productos: IProducto[] = [];

  editForm = this.fb.group({
    id: [],
    productoId: [null, Validators.required],
  });

  constructor(
    protected detalleVentaService: DetalleVentaService,
    protected productoService: ProductoService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ detalleVenta }) => {
      this.updateForm(detalleVenta);

      this.productoService.query().subscribe((res: HttpResponse<IProducto[]>) => (this.productos = res.body || []));
    });
  }

  updateForm(detalleVenta: IDetalleVenta): void {
    this.editForm.patchValue({
      id: detalleVenta.id,
      productoId: detalleVenta.productoId,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const detalleVenta = this.createFromForm();
    if (detalleVenta.id !== undefined) {
      this.subscribeToSaveResponse(this.detalleVentaService.update(detalleVenta));
    } else {
      this.subscribeToSaveResponse(this.detalleVentaService.create(detalleVenta));
    }
  }

  private createFromForm(): IDetalleVenta {
    return {
      ...new DetalleVenta(),
      id: this.editForm.get(['id'])!.value,
      productoId: this.editForm.get(['productoId'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IDetalleVenta>>): void {
    result.subscribe(
      () => this.onSaveSuccess(),
      () => this.onSaveError()
    );
  }

  protected onSaveSuccess(): void {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError(): void {
    this.isSaving = false;
  }

  trackById(index: number, item: IProducto): any {
    return item.id;
  }
}
