import React, { useState, useEffect } from 'react';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IStudent } from 'app/shared/model/student.model';
import { getEntities } from './studentUser.reducer';

export const StudentUser = () => {
  const dispatch = useAppDispatch();

  const location = useLocation();
  const navigate = useNavigate();

  const studentUserList = useAppSelector(state => state.studentUser.entities);
  const loading = useAppSelector(state => state.studentUser.loading);

  useEffect(() => {
    dispatch(getEntities({}));
  }, []);

  const handleSyncList = () => {
    dispatch(getEntities({}));
  };

  return (
    <div>
      <h2 id="student-heading" data-cy="StudentHeading">
        <Translate contentKey="noteToSchoolApp.student.home.title">Students</Translate>
        {/*         <div className="d-flex justify-content-end"> */}
        {/*           <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}> */}
        {/*             <FontAwesomeIcon icon="sync" spin={loading} />{' '} */}
        {/*             <Translate contentKey="noteToSchoolApp.student.home.refreshListLabel">Refresh List</Translate> */}
        {/*           </Button> */}
        {/*           <Link to="/student/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton"> */}
        {/*             <FontAwesomeIcon icon="plus" /> */}
        {/*             &nbsp; */}
        {/*             <Translate contentKey="noteToSchoolApp.student.home.createLabel">Create new Student</Translate> */}
        {/*           </Link> */}
        {/*         </div> */}
      </h2>
      <div className="table-responsive">
        {studentUserList && studentUserList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="noteToSchoolApp.studentUser.id">ID</Translate>
                </th>
                <th>
                  <Translate contentKey="noteToSchoolApp.studentUser.fullName">Full Name</Translate>
                </th>
                <th>
                  <Translate contentKey="noteToSchoolApp.studentUser.address">Address</Translate>
                </th>
                <th>
                  <Translate contentKey="noteToSchoolApp.studentUser.grade">Grade</Translate>
                </th>
                <th>
                  <Translate contentKey="noteToSchoolApp.studentUser.contactNo">Contact No</Translate>
                </th>
                <th>
                  <Translate contentKey="noteToSchoolApp.studentUser.user">User</Translate>
                </th>
                <th>
                  <Translate contentKey="noteToSchoolApp.studentUser.bus">Bus</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {studentUserList.map((studentUser, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    {studentUser.id}
                    {/*                     <Button tag={Link} to={`/studentUser/${studentUser.id}`} color="link" size="sm"> */}
                    {/*                       {studentUser.id} */}
                    {/*                     </Button> */}
                  </td>
                  <td>{studentUser.fullName}</td>
                  <td>{studentUser.address}</td>
                  <td>{studentUser.grade}</td>
                  <td>{studentUser.contactNo}</td>
                  <td>{studentUser.user ? studentUser.user.login : ''}</td>
                  <td>{studentUser.bus.name}</td>
                  {/*                   <td>{studentUser.bus ? <Link to={`/bus/${studentUser.bus.id}`}>{studentUser.bus.name}</Link> : ''}</td> */}
                  <td className="text-end">
                    <Button tag={Link} to={`/request-tracker/new`} color="primary" size="sm" data-cy="entityEditButton">
                      <FontAwesomeIcon icon="pencil-alt" /> Request
                    </Button>
                  </td>
                </tr>
              ))}
            </tbody>
          </Table>
        ) : (
          !loading && (
            <div className="alert alert-warning">
              <Translate contentKey="noteToSchoolApp.student.home.notFound">No Students found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

export default StudentUser;
