import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'cliente',
        loadChildren: () => import('./cliente/cliente.module').then(m => m.AppsoaClienteModule),
      },
      {
        path: 'producto',
        loadChildren: () => import('./producto/producto.module').then(m => m.AppsoaProductoModule),
      },
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ]),
  ],
})
export class AppsoaEntityModule {}
