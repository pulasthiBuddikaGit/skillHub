import React, { useEffect } from "react";
import { Bell } from "react-bootstrap-icons";
import { useDispatch, useSelector } from "react-redux";
import {
  getNotificationsByUserId,
  updateNotificationsById,
} from "../../app/actions/notification.action";
import { CiSquareRemove } from "react-icons/ci";
import { AiOutlineNotification } from "react-icons/ai";

function NotificationDropdown() {
  const dispatch = useDispatch();
  const userId = useSelector((state) => state.user.userId);
  const notifications = useSelector(
    (state) => state.notification.notifications
  );

  const styles = {
    container: { position: "relative", display: "inline-block" },
    dropdownToggle: {
      background: "none",
      border: "none",
      position: "relative",
    },
    bellIcon: { fontSize: "1.25rem" },
    badge: {
      position: "absolute",
      top: 0,
      right: 0,
      backgroundColor: "#dc3545",
      color: "white",
      borderRadius: "50%",
      minWidth: "18px",
      height: "18px",
      fontSize: "0.75rem",
      display: "flex",
      alignItems: "center",
      justifyContent: "center",
      transform: "translate(50%, -50%)",
    },
    notificationItem: {
      borderBottom: "1px solid rgba(0, 0, 0, 0.05)",
    },
    notificationIcon: {
      marginRight: "0.75rem",
      color: "#0d6efd",
    },
    notificationMessage: {
      flexGrow: 1,
      fontSize: "0.875rem",
      whiteSpace: "normal",
      wordBreak: "break-word",
    },
    removeButton: {
      cursor: "pointer",
    },
    loadingItem: {
      padding: "0.75rem 1rem",
      fontSize: "0.875rem",
      color: "#6c757d",
      textAlign: "center",
    },
    emptyItem: {
      padding: "0.75rem 1rem",
      fontSize: "0.875rem",
      color: "#6c757d",
      textAlign: "center",
    },
  };

  useEffect(() => {
    if (userId) {
      dispatch(getNotificationsByUserId(userId));
    }
  }, [dispatch, userId]);

  const handleOnRead = (id) => {
    const updateNotification = {
      id,
      isRead: true,
    };
    dispatch(updateNotificationsById(updateNotification));
  };
  return (
    <>
      <a
        className="nav-link dropdown-toggle"
        href="#"
        id="notification-dropdown"
        role="button"
        data-bs-toggle="dropdown"
        aria-expanded="false"
        style={{ color: "white" }}
        
      >
        <Bell />
      </a>
      <ul
        className="dropdown-menu dropdown-menu-end"
        aria-labelledby="notification-dropdown"
      >
        {notifications && notifications.length ? (
          [...notifications]
            .reverse()
            .slice(-5)
            .map((notification) => {
              return (
                <li key={notification.id}>
                  <a className="dropdown-item" href="#">
                    <AiOutlineNotification className="me-2" />
                    <span className="me-2">{notification.message}</span>
                    <CiSquareRemove
                      size={20}
                      className="text-danger"
                      onClick={() => {
                        handleOnRead(notification.id);
                      }}
                    />
                  </a>
                </li>
              );
            })
        ) : (
          <li>
            <a className="dropdown-item" href="#">
              <span className="me-2">No Notifications yet</span>
            </a>
          </li>
        )}
      </ul>
    </>
  );
}

export default NotificationDropdown;
