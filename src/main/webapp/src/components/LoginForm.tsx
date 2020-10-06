import React from 'react';
import {Formik, Form as FormikForm, FormikValues} from 'formik';
import * as Yup from 'yup';
import {Button, Form, FormControl, InputGroup} from "react-bootstrap";
import {APP_TEST_USER_PASSWORD, APP_TEST_USERNAME} from "../constants/AppConstants";

type ResultFormProps = {
    onSubmit: (values: FormikValues) => void;
}

const INITIAL_VALUES = {
    name: APP_TEST_USERNAME,
    password: APP_TEST_USER_PASSWORD
}

const FormSchema = Yup.object().shape({
    name: Yup.string()
        .required(),
    password: Yup.string()
        .required()
});

export const LoginForm: React.FC<ResultFormProps> = ({onSubmit}) => (
    <>
        <Formik
            initialValues={INITIAL_VALUES}
            validationSchema={FormSchema}
            onSubmit={ values => onSubmit(values)}>
            {({
                  values,
                  errors,
                  touched,
                  handleChange,
                  handleBlur, handleSubmit,
              }) => (
                <FormikForm>
                    <Form.Group controlId="formLastName">
                        <Form.Control
                            type="text"
                            name="name"
                            onChange={handleChange}
                            onBlur={handleBlur}
                            value={values.name}
                            className={touched.name && errors.name ? "error" : ""}
                        />
                        {touched.name && errors.name ? (
                            <div>{errors.name}</div>
                        ) : null}
                    </Form.Group>

                    <Form.Group controlId="formName">
                        <Form.Control
                            type="password"
                            name="password"
                            placeholder="Password"
                            onChange={handleChange}
                            onBlur={handleBlur}
                            value={values.password}
                            className={touched.password && errors.password ? "error" : ""}
                        />
                        {touched.name && errors.password ? (
                            <div>{errors.password}</div>
                        ) : null}
                    </Form.Group>

                    <div className="row center justify-content-center">
                        <Button type='submit' >
                            Log in
                        </Button>
                    </div>
                </FormikForm>
            )}
        </Formik>
    </>
);

