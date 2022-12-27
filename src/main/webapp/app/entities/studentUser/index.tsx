import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import StudentUser from './studentUser';
// import StudentUserDetail from './studentUser-detail';
// import StudentUserUpdate from './studentUser-update';
// import StudentUserDeleteDialog from './studentUser-delete-dialog';

const StudentUserRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<StudentUser />} />
    {/*     <Route path="new" element={<StudentUserUpdate />} /> */}
    <Route path=":id">
      {/*       <Route index element={<StudentUserDetail />} /> */}
      {/*       <Route path="edit" element={<StudentUserUpdate />} /> */}
      {/*       <Route path="delete" element={<StudentUserDeleteDialog />} /> */}
    </Route>
  </ErrorBoundaryRoutes>
);

export default StudentUserRoutes;
