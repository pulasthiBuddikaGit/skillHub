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
    <div className="container mb-3 card create">
      <div className="card-body">
        <form onSubmit={handleSubmit}>
          <h1 className="mt-2">Share Your Learning Progress</h1>
          <p className="text-muted">
            Let your peers know how you're growing and what you've achieved!
          </p>

          <div className="mt-2 mb-3">
            <label className="form-label fw-semibold">
              ✍️ What did you learn or accomplish today?
            </label>
            <textarea
              className="form-control"
              placeholder="write something ..."
              value={caption}
              onChange={(e) => setCaption(e.target.value)}
              rows="4"
            />
          </div>

          <i className="text-muted">📸 Upload up to 4 images (optional)</i>
          <div className="mb-3 mt-2">
            {imgLink.length > 0 && (
              <div className="d-flex flex-wrap gap-2">
                {imgLink.map((link, index) => (
                  <img
                    key={index}
                    src={link}
                    className="img-thumbnail"
                    alt={`Upload ${index + 1}`}
                    style={{ maxWidth: "150px", maxHeight: "150px" }}
                  />
                ))}
              </div>
            )}

            <input
              type="file"
              className="form-control mt-2"
              onChange={uploadImage}
              ref={fileInputRef}
              multiple
              accept="image/*"
            />
          </div>

          <button type="submit" className="btn btn-outline-primary mt-2">
            🚀 Post Progress
          </button>
        </form>
      </div>
    </div>
  );
}

export default PostAdd;
