import { Route } from '@angular/router';

import { VcamisetasComponent } from './vcamisetas.component';

export const VCAMISETA_ROUTE: Route = {
  path: '',
  component: VcamisetasComponent,
  data: {
    pageTitle: '/vcamisetas',
  },
};
