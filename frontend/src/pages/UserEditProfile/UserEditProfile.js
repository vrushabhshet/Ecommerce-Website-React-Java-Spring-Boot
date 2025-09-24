import React, {Component} from 'react';
import PropTypes from "prop-types";
import {connect} from "react-redux";
import {faEdit, faLock} from "@fortawesome/free-solid-svg-icons";
import {FontAwesomeIcon} from "@fortawesome/react-fontawesome";

import AccountNavbar from "../../component/AccountNavbar/AccountNavbar";
import {updateUserInfo} from "../../actions/user-actions";

class UserEditProfile extends Component {
    state = {password: ""};

    onFormSubmit = (event) => {
        event.preventDefault();

        const userData = {
            password: this.state.password,
            email: localStorage.getItem("email")
        };

        this.props.updateUserInfo(userData);
    }
    componentDidUpdate(prevProps) {
        if (!prevProps.user.success && this.props.user.success) {
            setTimeout(() => {
                this.props.history.push("/account");
            }, 2000); 
        }
    }

    handleInputChange = (event) => {
        const {name, value} = event.target;

        this.setState({
            [name]: value
        });
    };

    render() {
        return (
            <div className="container">
                <AccountNavbar/>
                <div className="container mt-5">
                    <h4><FontAwesomeIcon className="mr-2" icon={faLock}/> Change Password</h4>
                    {this.props.user.success && (
                        <div className="alert alert-success col-6" role="alert">Password successfully changed!</div>
                    )}
                    <form onSubmit={this.onFormSubmit}>
                        <div className="form-group row mt-5">
                            <label className="col-form-label mx-3">Enter a new password: </label>
                            <div className="col-sm-4">
                                <input
                                    type="password"
                                    name="password"
                                    className="form-control"
                                    value={this.state.password}
                                    onChange={this.handleInputChange}/>
                            </div>
                        </div>
                        <button type="submit" className="btn btn-dark">
                            <FontAwesomeIcon className="mr-2" icon={faEdit}/> Edit
                        </button>
                    </form>
                </div>
            </div>
        );
    }
}

UserEditProfile.propTypes = {
    updateUserInfo: PropTypes.func.isRequired,
    user: PropTypes.object.isRequired
};

const mapStateToProps = (state) => ({
    user: state.user
});

export default connect(mapStateToProps, {updateUserInfo})(UserEditProfile);
