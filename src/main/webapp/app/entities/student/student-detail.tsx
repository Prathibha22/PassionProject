import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './student.reducer';

export const StudentDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const studentEntity = useAppSelector(state => state.student.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="studentDetailsHeading">
          <Translate contentKey="noteToSchoolApp.student.detail.title">Student</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{studentEntity.id}</dd>
          <dt>
            <span id="fullName">
              <Translate contentKey="noteToSchoolApp.student.fullName">Full Name</Translate>
            </span>
          </dt>
          <dd>{studentEntity.fullName}</dd>
          <dt>
            <span id="address">
              <Translate contentKey="noteToSchoolApp.student.address">Address</Translate>
            </span>
          </dt>
          <dd>{studentEntity.address}</dd>
          <dt>
            <span id="grade">
              <Translate contentKey="noteToSchoolApp.student.grade">Grade</Translate>
            </span>
          </dt>
          <dd>{studentEntity.grade}</dd>
          <dt>
            <span id="contactNo">
              <Translate contentKey="noteToSchoolApp.student.contactNo">Contact No</Translate>
            </span>
          </dt>
          <dd>{studentEntity.contactNo}</dd>
          <dt>
            <Translate contentKey="noteToSchoolApp.student.user">User</Translate>
          </dt>
          <dd>{studentEntity.user ? studentEntity.user.login : ''}</dd>
          <dt>
            <Translate contentKey="noteToSchoolApp.student.bus">Bus</Translate>
          </dt>
          <dd>{studentEntity.bus ? studentEntity.bus.name : ''}</dd>
        </dl>
        <Button tag={Link} to="/student" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/student/${studentEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default StudentDetail;
