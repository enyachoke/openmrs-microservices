import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { GatewaySharedModule } from 'app/shared/shared.module';
import { DrugOrderComponent } from './drug-order.component';
import { DrugOrderDetailComponent } from './drug-order-detail.component';
import { DrugOrderUpdateComponent } from './drug-order-update.component';
import { DrugOrderDeleteDialogComponent } from './drug-order-delete-dialog.component';
import { drugOrderRoute } from './drug-order.route';

@NgModule({
  imports: [GatewaySharedModule, RouterModule.forChild(drugOrderRoute)],
  declarations: [DrugOrderComponent, DrugOrderDetailComponent, DrugOrderUpdateComponent, DrugOrderDeleteDialogComponent],
  entryComponents: [DrugOrderDeleteDialogComponent],
})
export class OrdersDrugOrderModule {}
