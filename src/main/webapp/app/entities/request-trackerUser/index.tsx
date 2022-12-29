import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import RequestTrackerUser from './request-trackerUser';
import RequestTrackerUserUpdate from './request-trackerUser-update';

const RequestTrackerUserRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<RequestTrackerUser />} />
    <Route path="new" element={<RequestTrackerUserUpdate />} />
    <Route path=":id"></Route>
  </ErrorBoundaryRoutes>
);

export default RequestTrackerUserRoutes;
