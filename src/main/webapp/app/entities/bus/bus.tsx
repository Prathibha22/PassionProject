import React, { useState, useEffect } from 'react';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IBus } from 'app/shared/model/bus.model';
import { getEntities } from './bus.reducer';

export const Bus = () => {
  const dispatch = useAppDispatch();

  const location = useLocation();
  const navigate = useNavigate();

  const busList = useAppSelector(state => state.bus.entities);
  const loading = useAppSelector(state => state.bus.loading);

  useEffect(() => {
    dispatch(getEntities({}));
  }, []);

  const handleSyncList = () => {
    dispatch(getEntities({}));
  };

  return (
    <div>
      <h2 id="bus-heading" data-cy="BusHeading">
        <Translate contentKey="noteToSchoolApp.bus.home.title">Buses</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="noteToSchoolApp.bus.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to="/bus/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="noteToSchoolApp.bus.home.createLabel">Create new Bus</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {busList && busList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="noteToSchoolApp.bus.id">ID</Translate>
                </th>
                <th>
                  <Translate contentKey="noteToSchoolApp.bus.name">Name</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {busList.map((bus, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/bus/${bus.id}`} color="link" size="sm">
                      {bus.id}
                    </Button>
                  </td>
                  <td>{bus.name}</td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`/bus/${bus.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`/bus/${bus.id}/edit`} color="primary" size="sm" data-cy="entityEditButton">
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`/bus/${bus.id}/delete`} color="danger" size="sm" data-cy="entityDeleteButton">
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
              <Translate contentKey="noteToSchoolApp.bus.home.notFound">No Buses found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

export default Bus;
