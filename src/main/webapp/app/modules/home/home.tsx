import './home.scss';

import React from 'react';
import { Link } from 'react-router-dom';
import { Translate } from 'react-jhipster';
import { Row, Col, Alert } from 'reactstrap';
import { Button, Table } from 'reactstrap';
import { useAppSelector } from 'app/config/store';

export const Home = () => {
  const account = useAppSelector(state => state.authentication.account);
  const users = useAppSelector(state => state.userManagement.users);

  return (
    <Row>
      <Col md="3" className="pad">
        <span className="hipster rounded" />
      </Col>
      <Col md="9">
        <h2>
          <Translate contentKey="home.title">Welcome, Parent!</Translate>
        </h2>
        <p className="lead">{/*           <Translate contentKey="home.subtitle">This is your homepage</Translate> */}</p>
        {account?.login ? (
          <div>
            <Alert color="success">
              <Translate contentKey="home.logged.message" interpolate={{ username: account.login }}>
                You are logged in as user {account.login}.
              </Translate>
            </Alert>
          </div>
        ) : (
          <div>
            <Alert color="warning">
              <Translate contentKey="global.messages.info.authenticated.prefix">If you want to </Translate>
              <Link to="/login" className="alert-link">
                <Translate contentKey="global.messages.info.authenticated.link"> sign in</Translate>
              </Link>
              <Translate contentKey="global.messages.info.authenticated.suffix">
                , you can try the default accounts:
                <br />- Administrator (login=&quot;admin&quot; and password=&quot;admin&quot;)
                <br />- User (login=&quot;user&quot; and password=&quot;user&quot;).
              </Translate>
            </Alert>
            <Alert color="warning">
              <Translate contentKey="global.messages.info.register.noaccount">You do not have an account yet?</Translate>&nbsp;
              <Link to="/account/register" className="alert-link">
                <Translate contentKey="global.messages.info.register.link">Register a new account</Translate>
              </Link>
            </Alert>
          </div>
        )}
        <div>
          <p>
            {/*            check to see user is login or not */}
            {account?.login ? (
              <div>
                <p>
                  {/*                checking the role of user */}
                  {account.authorities == 'ROLE_USER' ? (
                    <p>
                      <Alert color="warning">
                        <Translate contentKey="home.userlogin"></Translate>
                        <Link to="/studentUser" className="alert-link">
                          <Translate contentKey="home.details"> sign in</Translate>
                        </Link>
                        <Translate contentKey="home.studentinfo"> </Translate>
                      </Alert>
                    </p>
                  ) : (
                    <p>
                      <Alert color="warning">
                        <Translate contentKey="home.userlogin"></Translate>
                        <Link to="/request-tracker" className="alert-link">
                          {' '}
                          <Translate contentKey="home.details"> </Translate>
                        </Link>
                        <Translate contentKey="home.studentinfoadmin"> </Translate>
                      </Alert>
                      {/*                       <Button tag={Link} to="/request-tracker" replace color="info" data-cy="entityDetailsBackButton"> */}
                      {/*                         <span className="d-none d-md-inline">Click on the link to view the change request</span> */}
                      {/*                       </Button> */}
                    </p>
                  )}
                </p>
              </div>
            ) : (
              <div>
                <p></p>
              </div>
            )}
          </p>
        </div>
        <p>
          <Translate contentKey="home.question">If you have any question on JHipster:</Translate>
        </p>

        <p>
          <Translate contentKey="home.like">If you like JHipster, do not forget to give us a star on</Translate>{' '}
          <a href="https://github.com/Prathibha22/PassionProject/tree/main/src/main/webapp/app" target="_blank" rel="noopener noreferrer">
            GitHub
          </a>
          !
        </p>
      </Col>
    </Row>
  );
};

export default Home;
