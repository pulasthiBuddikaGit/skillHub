import React, { useEffect, useState } from "react";
import { useDispatch, useSelector } from "react-redux";
import { getUser, updateUserById } from "../../app/actions/user.actions";
import { deleteUserById } from "../../app/actions/user.actions";
import storage from "../../util/firebaseConfig";
import { ref, uploadBytesResumable, getDownloadURL } from "firebase/storage";

function Profile(props) {
  const dispatch = useDispatch();
  const user = useSelector((state) => state.user);
  
  // State for success message
  const [showSuccessMessage, setShowSuccessMessage] = useState(false);

  const [username, setUsername] = useState(user?.user?.username);
  const [email, setEmail] = useState(user?.user?.email);
  const [contactNumber, setContactNumber] = useState(user?.user?.contactNumber);
  const [country, setCountry] = useState(user?.user?.country);
  const [profileImage, setProfileImage] = useState(user?.user?.profileImage ? user.user.profileImage : null);
  const [isUploading, setIsUploading] = useState(false);
  const [uploadProgress, setUploadProgress] = useState(0);

  useEffect(() => {
    dispatch(getUser(user.userId));
  }, [dispatch]);

  const handleSubmit = () => {
    const userUpdate = {
      id: user.user.id,
      username,
      email,
      contactNumber,
      country,
      profileImage
    };

    dispatch(updateUserById(userUpdate));

    // Show success message
    setShowSuccessMessage(true);
    setTimeout(() => {
      setShowSuccessMessage(false);
    }, 3000); // Hide success message after 3 seconds

    props.closeModal();
  };

  const handleDelete = () => {
    if (window.confirm("Are you sure you want to delete your account?")) {
      dispatch(deleteUserById(user.userId));
      props.closeModal();
    }
  };
  
  const uploadImage = (e) => {
    const file = e.target.files[0];

    if (!file) {
      alert("Please upload an image first!");
      return;
    }

    setIsUploading(true);
    setUploadProgress(0);
    const storageRef = ref(storage, `/users/${file.name}`);
    const uploadTask = uploadBytesResumable(storageRef, file);

    uploadTask.on(
      "state_changed",
      (snapshot) => {
        const progress = Math.round((snapshot.bytesTransferred / snapshot.totalBytes) * 100);
        setUploadProgress(progress);
      },
      (err) => {
        console.log(err);
        setIsUploading(false);
      },
      () => {
        getDownloadURL(uploadTask.snapshot.ref).then((url) => {
          setProfileImage(url);
          setIsUploading(false);
        });
      }
    );
  };

  return (
    <div className="container-fluid p-0">
      <div className="card border-0 shadow">
        {/* Header */}
        <div className="card-header bg-primary bg-gradient text-white p-4 d-flex justify-content-between align-items-center">
          <h4 className="mb-0 fw-bold">Your Profile</h4>
          <button 
            type="button" 
            className="btn-close btn-close-white" 
            onClick={() => props.closeModal()}
            aria-label="Close"
          ></button>
        </div>
        
        {/* Profile Image */}
        <div className="text-center position-relative" style={{ marginTop: "-20px" }}>
          <div className="position-relative d-inline-block">
            <div 
              className="rounded-circle bg-white p-1 shadow" 
              style={{ width: "140px", height: "140px", overflow: "hidden" }}
            >
              {profileImage ? (
                <img 
                  src={profileImage} 
                  alt="Profile" 
                  className="rounded-circle img-fluid h-100 w-100 object-fit-cover"
                  style={{ objectFit: "cover" }} 
                />
              ) : (
                <div className="d-flex justify-content-center align-items-center h-100 bg-light">
                  <svg xmlns="http://www.w3.org/2000/svg" width="60" height="60" fill="currentColor" className="bi bi-person text-secondary" viewBox="0 0 16 16">
                    <path d="M8 8a3 3 0 1 0 0-6 3 3 0 0 0 0 6zm2-3a2 2 0 1 1-4 0 2 2 0 0 1 4 0zm4 8c0 1-1 1-1 1H3s-1 0-1-1 1-4 6-4 6 3 6 4zm-1-.004c-.001-.246-.154-.986-.832-1.664C11.516 10.68 10.289 10 8 10c-2.29 0-3.516.68-4.168 1.332-.678.678-.83 1.418-.832 1.664h10z"/>
                  </svg>
                </div>
              )}
            </div>
            
            <label 
              htmlFor="profileImageUpload" 
              className="btn btn-sm btn-primary rounded-circle position-absolute bottom-0 end-0 shadow"
              style={{ cursor: "pointer" }}
            >
              <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" className="bi bi-camera" viewBox="0 0 16 16">
                <path d="M15 12a1 1 0 0 1-1 1H2a1 1 0 0 1-1-1V6a1 1 0 0 1 1-1h1.172a3 3 0 0 0 2.12-.879l.83-.828A1 1 0 0 1 6.827 3h2.344a1 1 0 0 1 .707.293l.828.828A3 3 0 0 0 12.828 5H14a1 1 0 0 1 1 1v6zM2 4a2 2 0 0 0-2 2v6a2.002 2.002 0 0 0 2 2h12a2.002 2.002 0 0 0 2-2V6a2 2 0 0 0-2-2h-1.172a2 2 0 0 1-1.414-.586l-.828-.828A2 2 0 0 0 9.172 2H6.828a2 2 0 0 0-1.414.586l-.828.828A2 2 0 0 1 3.172 4H2z"/>
                <path d="M8 11a2.5 2.5 0 1 1 0-5 2.5 2.5 0 0 1 0 5zm0 1a3.5 3.5 0 1 0 0-7 3.5 3.5 0 0 0 0 7zM3 6.5a.5.5 0 1 1-1 0 .5.5 0 0 1 1 0z"/>
              </svg>
              <input 
                id="profileImageUpload" 
                type="file" 
                className="d-none" 
                onChange={uploadImage}
                accept="image/*"
              />
            </label>
          </div>
        </div>
        
        {/* Upload Progress */}
        {isUploading && (
          <div className="px-4 mt-3">
            <div className="progress" style={{ height: "8px" }}>
              <div 
                className="progress-bar progress-bar-striped progress-bar-animated bg-primary" 
                role="progressbar" 
                style={{ width: `${uploadProgress}%` }} 
                aria-valuenow={uploadProgress} 
                aria-valuemin="0" 
                aria-valuemax="100"
              ></div>
            </div>
            <p className="text-center text-muted small mt-1">Uploading image... {uploadProgress}%</p>
          </div>
        )}
        
        {/* Form */}
        <div className="card-body p-4">
          <form>
            <div className="row g-3">
              <div className="col-12">
                <div className="form-floating mb-3">
                  <input
                    type="text"
                    className="form-control bg-light"
                    id="username"
                    placeholder="Username"
                    value={username}
                    readOnly
                  />
                  <label htmlFor="username">Username</label>
                </div>
              </div>
              
              <div className="col-12">
                <div className="form-floating mb-3">
                  <input
                    type="email"
                    className="form-control"
                    id="email"
                    placeholder="Email Address"
                    value={email}
                    onChange={(e) => setEmail(e.target.value)}
                    pattern="[a-z0-9._%+-]+@[a-z0-9.-]+\.[a-z]{2,}$"
                  />
                  <label htmlFor="email">Email Address</label>
                </div>
              </div>
              
              <div className="col-md-6">
                <div className="form-floating mb-3">
                  <input
                    type="text"
                    className="form-control"
                    id="contactNumber"
                    placeholder="Contact Number"
                    value={contactNumber}
                    onChange={(e) => setContactNumber(e.target.value)}
                    pattern="[0-9]{10}"
                  />
                  <label htmlFor="contactNumber">Contact Number</label>
                </div>
              </div>
              
              <div className="col-md-6">
                <div className="form-floating mb-3">
                  <input
                    type="text"
                    className="form-control"
                    id="country"
                    placeholder="Country"
                    value={country}
                    onChange={(e) => setCountry(e.target.value)}
                  />
                  <label htmlFor="country">Country</label>
                </div>
              </div>
            </div>
            
            {/* Profile Image Actions */}
            {profileImage && (
              <div className="d-flex justify-content-center mt-3">
                <button 
                  type="button"
                  onClick={() => setProfileImage("https://i.discogs.com/57iTb7iRduipsfyksYodpaSpz_eEjtg52zPBhCwBPhI/rs:fit/g:sm/q:40/h:300/w:300/czM6Ly9kaXNjb2dz/LWRhdGFiYXNlLWlt/YWdlcy9BLTY5Nzg2/ODEtMTU0OTgxMTIz/OC02NjMxLmpwZWc.jpeg")} 
                  className="btn btn-link text-danger p-0"
                >
                  <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" className="bi bi-trash3 me-1" viewBox="0 0 16 16">
                    <path d="M6.5 1h3a.5.5 0 0 1 .5.5v1H6v-1a.5.5 0 0 1 .5-.5ZM11 2.5v-1A1.5 1.5 0 0 0 9.5 0h-3A1.5 1.5 0 0 0 5 1.5v1H2.506a.58.58 0 0 0-.01 0H1.5a.5.5 0 0 0 0 1h.538l.853 10.66A2 2 0 0 0 4.885 16h6.23a2 2 0 0 0 1.994-1.84l.853-10.66h.538a.5.5 0 0 0 0-1h-.995a.59.59 0 0 0-.01 0H11Zm1.958 1-.846 10.58a1 1 0 0 1-.997.92h-6.23a1 1 0 0 1-.997-.92L3.042 3.5h9.916Zm-7.487 1a.5.5 0 0 1 .528.47l.5 8.5a.5.5 0 0 1-.998.06L5 5.03a.5.5 0 0 1 .47-.53Zm5.058 0a.5.5 0 0 1 .47.53l-.5 8.5a.5.5 0 1 1-.998-.06l.5-8.5a.5.5 0 0 1 .528-.47ZM8 4.5a.5.5 0 0 1 .5.5v8.5a.5.5 0 0 1-1 0V5a.5.5 0 0 1 .5-.5Z"/>
                  </svg>
                  Reset Profile Picture
                </button>
              </div>
            )}
            
            {/* Success Message */}
            {showSuccessMessage && (
              <div className="alert alert-success d-flex align-items-center mt-4" role="alert">
                <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" fill="currentColor" className="bi bi-check-circle-fill flex-shrink-0 me-2" viewBox="0 0 16 16">
                  <path d="M16 8A8 8 0 1 1 0 8a8 8 0 0 1 16 0zm-3.97-3.03a.75.75 0 0 0-1.08.022L7.477 9.417 5.384 7.323a.75.75 0 0 0-1.06 1.06L6.97 11.03a.75.75 0 0 0 1.079-.02l3.992-4.99a.75.75 0 0 0-.01-1.05z"/>
                </svg>
                <div>Profile updated successfully!</div>
              </div>
            )}
            
            {/* Action Buttons */}
            <div className="d-grid gap-2 mt-4">
              <button
                type="button"
                onClick={handleSubmit}
                className="btn btn-primary btn-lg"
              >
                Save Changes
              </button>
              
              <button
                type="button"
                onClick={() => props.closeModal()}
                className="btn btn-light btn-lg"
              >
                Cancel
              </button>
              
              <button
                type="button"
                onClick={handleDelete}
                className="btn btn-outline-danger btn-lg"
              >
                Delete Account
              </button>
            </div>
            
          </form>
        </div>
      </div>
    </div>
  );
}

export default Profile;