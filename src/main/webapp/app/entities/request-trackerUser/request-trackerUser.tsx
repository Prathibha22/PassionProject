import React, { useState, useEffect } from 'react';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IRequestTracker } from 'app/shared/model/request-tracker.model';
import { getEntities } from './request-trackerUser.reducer';

export const RequestTrackerUser = () => {
  const dispatch = useAppDispatch();

  const location = useLocation();
  const navigate = useNavigate();

  const requestTrackerListUser = useAppSelector(state => state.requestTrackerUser.entities);
  const loading = useAppSelector(state => state.requestTrackerUser.loading);
  useEffect(() => {
    dispatch(getEntities({}));
  }, []);

  const handleSyncList = () => {
    dispatch(getEntities({}));
  };

  return (
    <div>
      <div className="table-responsive">
        {requestTrackerListUser && requestTrackerListUser.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="noteToSchoolApp.requestTracker.id">ID</Translate>
                </th>
                <th>
                  <Translate contentKey="noteToSchoolApp.requestTracker.date">Date</Translate>
                </th>
                <th>
                  <Translate contentKey="noteToSchoolApp.requestTracker.requestType">Request Type</Translate>
                </th>
                <th>
                  <Translate contentKey="noteToSchoolApp.requestTracker.description">Description</Translate>
                </th>
                <th>
                  <Translate contentKey="noteToSchoolApp.requestTracker.student">Student</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {requestTrackerListUser.map((requestTracker, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/request-tracker/${requestTracker.id}`} color="link" size="sm">
                      {requestTracker.id}
                    </Button>
                  </td>
                  <td>
                    {requestTracker.date ? <TextFormat type="date" value={requestTracker.date} format={APP_LOCAL_DATE_FORMAT} /> : null}
                  </td>
                  <td>
                    <Translate contentKey={`noteToSchoolApp.RequestType.${requestTracker.requestType}`} />
                  </td>
                  <td>{requestTracker.description}</td>
                  <td>
                    {requestTracker.student ? (
                      <Link to={`/student/${requestTracker.student.id}`}>{requestTracker.student.fullName}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`/request-tracker/${requestTracker.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`/request-tracker/${requestTracker.id}/edit`}
                        color="primary"
                        size="sm"
                        data-cy="entityEditButton"
                      >
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`/request-tracker/${requestTracker.id}/delete`}
                        color="danger"
                        size="sm"
                        data-cy="entityDeleteButton"
                      >
                        <FontAwesomeIcon icon="trash" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.delete">Delete</Translate>
                        </span>
                      </Button>
                    </div>
                  </td>
                </tr>
              ))}
            </tbody>
          </Table>
        ) : (
          !loading && (
            <div className="alert alert-warning">
              <Translate contentKey="noteToSchoolApp.requestTracker.home.notFound">No Request Trackers found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};
export default RequestTrackerUser;
