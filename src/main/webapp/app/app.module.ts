import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import './vendor';
import { AppsoaSharedModule } from 'app/shared/shared.module';
import { AppsoaCoreModule } from 'app/core/core.module';
import { AppsoaAppRoutingModule } from './app-routing.module';
import { AppsoaHomeModule } from './home/home.module';
import { AppsoaEntityModule } from './entities/entity.module';
// jhipster-needle-angular-add-module-import JHipster will add new module here
import { MainComponent } from './layouts/main/main.component';
import { NavbarComponent } from './layouts/navbar/navbar.component';
import { FooterComponent } from './layouts/footer/footer.component';
import { PageRibbonComponent } from './layouts/profiles/page-ribbon.component';
import { ErrorComponent } from './layouts/error/error.component';

@NgModule({
  imports: [
    BrowserModule,
    AppsoaSharedModule,
    AppsoaCoreModule,
    AppsoaHomeModule,
    // jhipster-needle-angular-add-module JHipster will add new module here
    AppsoaEntityModule,
    AppsoaAppRoutingModule,
  ],
  declarations: [MainComponent, NavbarComponent, ErrorComponent, PageRibbonComponent, FooterComponent],
  bootstrap: [MainComponent],
})
export class AppsoaAppModule {}
