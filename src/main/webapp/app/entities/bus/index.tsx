import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Bus from './bus';
import BusDetail from './bus-detail';
import BusUpdate from './bus-update';
import BusDeleteDialog from './bus-delete-dialog';

const BusRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Bus />} />
    <Route path="new" element={<BusUpdate />} />
    <Route path=":id">
      <Route index element={<BusDetail />} />
      <Route path="edit" element={<BusUpdate />} />
      <Route path="delete" element={<BusDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default BusRoutes;
