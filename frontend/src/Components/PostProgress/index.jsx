import React, { useRef } from "react";
import { useDispatch, useSelector } from "react-redux";
import { getPostsByUserId, savePost } from "../../app/actions/post.actions";
import storage from "../../util/firebaseConfig";
import { ref, uploadBytesResumable, getDownloadURL } from "firebase/storage";

function PostAdd() {
  const dispatch = useDispatch();
  const user = useSelector((state) => state.user);
  const fileInputRef = useRef(null);

  const [caption, setCaption] = React.useState("");
  const [imgLink, setImgLink] = React.useState([]);

  const handleSubmit = async (e) => {
    e.preventDefault();
    const post = {
      userId: user.userId,
      caption,
      imgLink,
    };
    await dispatch(savePost(post));
    await dispatch(getPostsByUserId(user.userId));
    setCaption("");
    setImgLink([]);
    fileInputRef.current.value = "";
  };

  const uploadImage = (e) => {
    const files = e.target.files;
    if (files.length === 0) {
      alert("Please upload at least one image!");
      return;
    }

    const maxImages = 4;
    const numImages = Math.min(maxImages, files.length);

    for (let i = 0; i < numImages; i++) {
      const file = files[i];
      const storageRef = ref(storage, `/posts/${file.name}`);
      const uploadTask = uploadBytesResumable(storageRef, file);

      uploadTask.on(
        "state_changed",
        () => {},
        (err) => console.log(err),
        () => {
          getDownloadURL(uploadTask.snapshot.ref).then((url) => {
            setImgLink((prevLinks) => [...prevLinks, url]);
          });
        }
      );
    }
  };

  return (
    <div
      style={{
        marginBottom: "1rem", // mb-3
        padding: "1.5rem", // card-body
        borderRadius: "0.5rem", // rounded
        boxShadow: "0 0.5rem 1rem rgba(0, 0, 0, 0.15)", // shadow-lg
        backgroundColor: "#f9f9fb",
        border: "1px solid #dee2e6",
        maxWidth: "600px",
        margin: "2rem auto",// center and spacing from top
      }}
    >
      <form onSubmit={handleSubmit}>
        <h1
          style={{
            marginTop: "0.5rem", // mt-2
            marginBottom: "1rem",
            fontWeight: "700", // fw-bold
            fontSize: "1.75rem",
            color: "#000",
            backgroundColor: "#f9f9fb",
            padding: "0.5rem",
            borderRadius: "0.5rem"
          }}
        >
          Share Your Learning Progress
        </h1>
        <p
          style={{
            color: "#6c757d",
            marginBottom: "1rem",
            fontSize: "1rem"
          }}
        >
          Let your peers know how you're growing and what you've achieved!
        </p>
  
        <div style={{ marginTop: "0.75rem", marginBottom: "1rem" }}>
          <label
            style={{
              fontWeight: "600",
              color: "#212529",
              display: "block",
              marginBottom: "0.5rem",
              fontSize: "1rem"
            }}
          >
            ✍️ What did you learn or accomplish today?
          </label>
          <textarea
            style={{
              width: "100%",
              fontSize: "1rem",
              border: "1px solid #33407e", // border-primary
              borderRadius: "0.375rem", // .form-control radius
              padding: "0.75rem",
              backgroundColor: "#ffffff",
              color: "#212529",
              resize: "vertical",
              lineHeight: "1.5"
            }}
            placeholder="Write Something..."
            value={caption}
            onChange={(e) => setCaption(e.target.value)}
            rows="4"
          />
        </div>
  
        <div style={{ marginBottom: "0.5rem", color: "#6c757d", fontSize: "0.9rem" }}>
          📸 Upload up to 4 images (optional)
        </div>
  
        {imgLink.length > 0 && (
          <div
            style={{
              display: "flex",
              flexWrap: "wrap",
              gap: "0.5rem",
              marginBottom: "1rem"
            }}
          >
            {imgLink.map((link, index) => (
              <img
                key={index}
                src={link}
                alt={`Upload ${index + 1}`}
                style={{
                  width: "150px",
                  height: "150px",
                  objectFit: "cover",
                  border: "1px solid #0dcaf0",
                  borderRadius: "0.5rem",
                  backgroundColor: "#ffffff"
                }}
              />
            ))}
          </div>
        )}
  
        <input
          type="file"
          onChange={uploadImage}
          ref={fileInputRef}
          multiple
          accept="image/*"
          style={{
            display: "block",
            width: "100%",
            padding: "0.5rem",
            backgroundColor: "#ffffff",
            border: "1px solidrgb(255, 255, 255)",
            borderRadius: "0.375rem",
            marginBottom: "1rem",
            fontSize: "1rem"
          }}
        />
  
        <button
          type="submit"
          style={{
            padding: "0.6rem 1.2rem",
            backgroundColor: "#33407e", // btn-primary
            color: "#ffffff",
            border: "none",
            borderRadius: "0.375rem", // btn radius
            fontWeight: "600",
            cursor: "pointer",
            fontSize: "1rem",
            boxShadow: "0 0.5rem 1remrgba(0, 0, 0, 0)"
          }}
        >
          🚀 Post Progress
        </button>
      </form>
    </div>
  );
  
  
  
}

export default PostAdd;
