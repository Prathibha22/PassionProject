import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';
import StudentUser from './studentUser';

const StudentUserRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<StudentUser />} />
    <Route path=":id"></Route>
  </ErrorBoundaryRoutes>
);

export default StudentUserRoutes;
