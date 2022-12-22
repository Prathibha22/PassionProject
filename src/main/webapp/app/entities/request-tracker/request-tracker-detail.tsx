import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './request-tracker.reducer';

export const RequestTrackerDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const requestTrackerEntity = useAppSelector(state => state.requestTracker.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="requestTrackerDetailsHeading">
          <Translate contentKey="noteToSchoolApp.requestTracker.detail.title">RequestTracker</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{requestTrackerEntity.id}</dd>
          <dt>
            <span id="date">
              <Translate contentKey="noteToSchoolApp.requestTracker.date">Date</Translate>
            </span>
          </dt>
          <dd>
            {requestTrackerEntity.date ? <TextFormat value={requestTrackerEntity.date} type="date" format={APP_LOCAL_DATE_FORMAT} /> : null}
          </dd>
          <dt>
            <span id="requestType">
              <Translate contentKey="noteToSchoolApp.requestTracker.requestType">Request Type</Translate>
            </span>
          </dt>
          <dd>{requestTrackerEntity.requestType}</dd>
          <dt>
            <span id="description">
              <Translate contentKey="noteToSchoolApp.requestTracker.description">Description</Translate>
            </span>
          </dt>
          <dd>{requestTrackerEntity.description}</dd>
          <dt>
            <Translate contentKey="noteToSchoolApp.requestTracker.student">Student</Translate>
          </dt>
          <dd>{requestTrackerEntity.student ? requestTrackerEntity.student.fullName : ''}</dd>
        </dl>
        <Button tag={Link} to="/request-tracker" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/request-tracker/${requestTrackerEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default RequestTrackerDetail;
