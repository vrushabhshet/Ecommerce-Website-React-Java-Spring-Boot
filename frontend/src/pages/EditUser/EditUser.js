import React, {Component} from 'react';
import PropTypes from "prop-types";
import {connect} from "react-redux";
import {faEdit, faUserEdit} from "@fortawesome/free-solid-svg-icons";
import {FontAwesomeIcon} from "@fortawesome/react-fontawesome";

import AccountNavbar from "../../component/AccountNavbar/AccountNavbar";
import {fetchUser, updateUser} from "../../actions/admin-actions";

class EditUser extends Component {
    state = {
        username: "",
        roles: ""
    };

    componentDidMount() {
        this.props.fetchUser(this.props.match.params.id);
    }


    componentWillReceiveProps(nextProps, nextContext) {
        const user = nextProps.user || {};
        // roles should be a string: "ADMIN" or "USER"
        this.setState({
            username: user.username || "",
            roles: Array.isArray(user.roles) ? (user.roles[0] || "") : (user.roles || "")
        });
    }


    onFormSubmit = (event) => {
        event.preventDefault();
        const { username, roles } = this.state;
        const { user } = this.props;
        // Only send username and roles as string, plus id
        this.props.updateUser({
            username,
            roles,
            id: user.id
        });
    }

    handleInputChange = (event) => {
        const { name, value } = event.target;
        this.setState({
            [name]: value
        });
    };

    render() {
    const { username, roles } = this.state;
    const { success } = this.props;

        return (
            <div>
                <AccountNavbar/>
                <div className="container mt-5">
                    <h4><FontAwesomeIcon className="mr-2" icon={faUserEdit}/> User: {username}</h4>
                    {success && (
                        <div className="alert alert-success" role="alert">
                            User updated successfully!
                        </div>
                    )}
                    <form onSubmit={this.onFormSubmit}>
                        <div className="form-group row mt-5">
                            <label className="col-sm-2 col-form-label">User name: </label>
                            <div className="col-sm-4">
                                <input
                                    type="text"
                                    className="form-control"
                                    name="username"
                                    value={username}
                                    onChange={this.handleInputChange}/>
                            </div>
                        </div>
                        <div className="form-group row">
                            <label className="col-sm-2 col-form-label">Role: </label>
                            <div className="col-sm-6">
                                <div className="form-check form-check-inline">
                                    <label className="form-check-label mr-1" htmlFor="inlineRadioUser">USER</label>
                                    <input
                                        id="inlineRadioUser"
                                        type="radio"
                                        className="form-check-input"
                                        name="roles"
                                        value="USER"
                                        checked={roles === "USER"}
                                        onChange={this.handleInputChange}/>
                                </div>
                                <div className="form-check form-check-inline">
                                    <label className="form-check-label mr-1" htmlFor="inlineRadioAdmin">ADMIN</label>
                                    <input
                                        id="inlineRadioAdmin"
                                        type="radio"
                                        className="form-check-input"
                                        name="roles"
                                        value="ADMIN"
                                        checked={roles === "ADMIN"}
                                        onChange={this.handleInputChange}/>
                                </div>
                            </div>
                        </div>
                        <button type="submit" className="btn btn-dark">
                            <FontAwesomeIcon className="mr-2" icon={faEdit}/>Save
                        </button>
                    </form>
                </div>
            </div>
        );
    }
}

EditUser.propTypes = {
    fetchUser: PropTypes.func.isRequired,
    user: PropTypes.object.isRequired
};

const mapStateToProps = (state) => ({
    user: state.admin.user,
    success: state.admin.success
});

export default connect(mapStateToProps, {fetchUser, updateUser})(EditUser);
