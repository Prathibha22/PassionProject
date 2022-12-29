import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IStudent } from 'app/shared/model/student.model';
import { getEntities as getStudents } from 'app/entities/studentUser/studentUser.reducer';
import { IRequestTracker } from 'app/shared/model/request-tracker.model';
import { RequestType } from 'app/shared/model/enumerations/request-type.model';
import { getEntity, updateEntity, createEntity, reset } from './request-trackerUser.reducer';

export const RequestTrackerUserUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const students = useAppSelector(state => state.studentUser.entities);
  const requestTrackerEntity = useAppSelector(state => state.requestTracker.entity);
  const loading = useAppSelector(state => state.requestTracker.loading);
  const updating = useAppSelector(state => state.requestTracker.updating);
  const updateSuccess = useAppSelector(state => state.requestTracker.updateSuccess);
  const requestTypeValues = Object.keys(RequestType);

  const handleClose = () => {
    navigate('/request-tracker');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getStudents({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    const entity = {
      ...requestTrackerEntity,
      ...values,
      student: students.find(it => it.id.toString() === values.student.toString()),
    };

    if (isNew) {
      dispatch(createEntity(entity));
    } else {
      dispatch(updateEntity(entity));
    }
  };

  const defaultValues = () =>
    isNew
      ? {}
      : {
          requestType: 'ABSENT',
          ...requestTrackerEntity,
          student: requestTrackerEntity?.student?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="noteToSchoolApp.requestTracker.home.createOrEditLabel" data-cy="RequestTrackerCreateUpdateHeading">
            <Translate contentKey="noteToSchoolApp.requestTracker.home.createOrEditLabel">Create or edit a RequestTracker</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? (
                <ValidatedField
                  name="id"
                  required
                  readOnly
                  id="request-tracker-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('noteToSchoolApp.requestTracker.date')}
                id="request-tracker-date"
                name="date"
                data-cy="date"
                type="date"
              />
              <ValidatedField
                label={translate('noteToSchoolApp.requestTracker.requestType')}
                id="request-tracker-requestType"
                name="requestType"
                data-cy="requestType"
                type="select"
              >
                {requestTypeValues.map(requestType => (
                  <option value={requestType} key={requestType}>
                    {translate('noteToSchoolApp.RequestType.' + requestType)}
                  </option>
                ))}
              </ValidatedField>
              <ValidatedField
                label={translate('noteToSchoolApp.requestTracker.description')}
                id="request-tracker-description"
                name="description"
                data-cy="description"
                type="text"
              />
              <ValidatedField
                id="request-tracker-student"
                name="student"
                data-cy="student"
                label={translate('noteToSchoolApp.requestTracker.student')}
                type="select"
              >
                <option value="" key="0" />
                {students
                  ? students.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.fullName}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/request-tracker" replace color="info">
                <FontAwesomeIcon icon="arrow-left" />
                &nbsp;
                <span className="d-none d-md-inline">
                  <Translate contentKey="entity.action.back">Back</Translate>
                </span>
              </Button>
              &nbsp;
              <Button color="primary" id="save-entity" data-cy="entityCreateSaveButton" type="submit" disabled={updating}>
                <FontAwesomeIcon icon="save" />
                &nbsp;
                <Translate contentKey="entity.action.save">Save</Translate>
              </Button>
            </ValidatedForm>
          )}
        </Col>
      </Row>
    </div>
  );
};

export default RequestTrackerUserUpdate;
