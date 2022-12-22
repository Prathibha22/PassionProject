import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import RequestTracker from './request-tracker';
import RequestTrackerDetail from './request-tracker-detail';
import RequestTrackerUpdate from './request-tracker-update';
import RequestTrackerDeleteDialog from './request-tracker-delete-dialog';

const RequestTrackerRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<RequestTracker />} />
    <Route path="new" element={<RequestTrackerUpdate />} />
    <Route path=":id">
      <Route index element={<RequestTrackerDetail />} />
      <Route path="edit" element={<RequestTrackerUpdate />} />
      <Route path="delete" element={<RequestTrackerDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default RequestTrackerRoutes;
