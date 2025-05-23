import React, { useState, useEffect } from "react";
import Modal from "react-modal";
import { useDispatch, useSelector } from "react-redux";
import { Link, useNavigate } from "react-router-dom";
import { getUser } from "../../app/actions/user.actions";
import { logout } from "../../app/slices/user.slice";
import Profile from "../../Pages/Profile";
import NotificationDropdown from "../NotificationDropdown";
import UserImage from "../../assets/user.jpeg";
import LogoImage from "../../assets/modern-camera-broken-logotype.png";

Modal.setAppElement("div");


function Navbar() {
  const dispatch = useDispatch();
  const navigate = useNavigate();
  const user = useSelector((state) => state.user);
  const [modalIsOpen, setModalIsOpen] = useState(false);
  
  function openModal() {
    setModalIsOpen(true);
  }

  function closeModal() {
    setModalIsOpen(false);
  }

  useEffect(() => {
    if (
      sessionStorage.getItem("Authorization") &&
      sessionStorage.getItem("userId")
    ) {
      if (!user.loginStatus) {
        dispatch(getUser(sessionStorage.getItem("userId")));
      }
    }

    if (!sessionStorage.getItem("Authorization")) {
      navigate("/login");
    }
  }, [dispatch, user.loginStatus]);

  return (
    <div>
      <nav className={`navbar navbar-expand-lg p-3 ${user.loginStatus ? "navbar-dark" : "navbar-light bg-light"}`} 
        style={user.loginStatus ? {background: "linear-gradient(to right,rgb(221, 226, 231),rgb(39, 61, 112))"} : {}}
      >
        <div className="container-fluid">
          <Link className="navbar-brand" to="/">
            <img
              src={LogoImage}
              className="tastebuds logo"
              alt=" "
              height="55px"
              width="auto"
            />
          </Link>
          <button
            className="navbar-toggler"
            type="button"
            data-bs-toggle="collapse"
            data-bs-target="#navbarSupportedContent"
            aria-controls="navbarSupportedContent"
            aria-expanded="false"
            aria-label="Toggle navigation"
          >
            <span className="navbar-toggler-icon"></span>
          </button>

          
          

          <div className="collapse navbar-collapse" id="navbarSupportedContent">
            <ul className="navbar-nav me-auto mb-2 mb-lg-0"></ul>
            {user.loginStatus ? (
              <div className="d-flex align-items-center" >
                <Link className="nav-link me-3" to="/" style={{ color: "white" }}>
                  Home
                </Link>
                <Link className="nav-link me-3" to="/user"style={{ color: "white" }}>
                  Profile
                </Link>
                {/* <Link className="nav-link me-3" to="/meals">
                  Meals
                </Link> */}
                <Link className="nav-link me-3" to="/progress"style={{ color: "white" }}>
                  Progress Posts
                </Link> 
                <NotificationDropdown style={{ color: "white" }}/>
                <button
                  className="btn btn-outline-danger me-3"
                  onClick={() => {
                    dispatch(logout());
                  }}style={{ color: "white" ,borderColor: "white" }}
                >
                  Logout
                </button>

                <Link
                  onClick={() => {
                    openModal();
                  }}
                  className="d-flex align-items-center text-decoration-none"
                  style={{color: "white"}}
                >
                  <img
                    src={
                      user?.user?.profileImage
                        ? user.user.profileImage
                        : UserImage
                    }
                    className="user-profile-image img-fluid me-1"
                    alt="Profile"
                  />
                  <span className="fw-bold">{user?.user?.username}</span>
                </Link>

              </div>
            ) : (
              //if user is not logged in
              <div>
                <Link to="/login" className="btn btn-primary me-3">
                  Login
                </Link>
                <Link to="/signup" className="btn btn-success">
                  Sign Up
                </Link>
              </div>
            )}
          </div>
        </div>
      </nav>

      <Modal
        isOpen={modalIsOpen}
        onRequestClose={closeModal}
        contentLabel="Profile Modal"
        style={customModalStyles}
      >
        <div className="p-2">
          <Profile closeModal={closeModal} />
        </div>
      </Modal>
      
    </div>
  );
}

// Custom modal styles to fix white background issue
const customModalStyles = {
  content: {
    top: '50%',
    left: '50%',
    right: 'auto',
    bottom: 'auto',
    marginRight: '-50%',
    transform: 'translate(-50%, -50%)',
    padding: '0',
    border: 'none',
    borderRadius: '8px',
    boxShadow: '0 4px 15px rgba(0,0,0,0.2)',
    backgroundColor: 'transparent'
  },
  overlay: {
    backgroundColor: 'rgba(0, 0, 0, 0.5)'
  }
};

export default Navbar;
