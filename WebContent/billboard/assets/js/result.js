import { postMusic } from "./assets/js/post-music.js";
const useState = React.useState;
const useEffect = React.useEffect;

const Result = () => {
  const [albums, setAlbums] = useState(null);
  const [isLoading, setIsLoading] = useState(false);
  const [error, setError] = useState("");
  const [requestResult, setRequestResult] = useState("");
  const url = new URL(window.location.href);
  const params = url.searchParams;
  const handleRequest = async (
    trackId,
    trackName,
    artistName,
    albumName,
    albumImageURL
  ) => {
    console.log("aaaa");
    const isRequested = await postMusic(
      trackId,
      trackName,
      artistName,
      albumName,
      albumImageURL
    );
    if (isRequested) {
      setRequestResult("送信に成功しました");
    } else setRequestResult("送信に失敗しました");
  };

  useEffect(() => {
    setIsLoading(true);
    if (!params.get("value")) {
      setError("取得できませんでした");
      setIsLoading(false);
    } else {
      fetch(`https://jsonplaceholder.typicode.com/albums`)
        .then((response) => {
          if (!response.ok) {
            throw new Error("error");
          }
          console.log(response);
          return response.json();
        })
        .then((data) => {
          setAlbums(data);
          setIsLoading(false);
          console.log(data);
        })
        .catch((error) => {
          setError("取得できませんでした");
          setIsLoading(false);
        });
    }
  }, []);

  return (
    <>
      <Header />
      <h1>検索結果</h1>
      {albums &&
        albums.map((album) => (
          <Album album={album} handleRequest={handleRequest} />
        ))}
      <div>
        {isLoading && <p>Loading...</p>}
        {error && <p>{error}</p>}
        <a href="./search.html">検索画面へ戻る</a>
      </div>
    </>
  );
};

const Album = ({ album, handleRequest }) => {
  return (
    <div className="album">
      <img src="./assets/img/entertainment_music.png" alt="album art" />
      <p>ID: {album.id}</p>
      <p>アルバム名: {album.title}</p>
      <p>曲名: hogehoge</p>
      <button onClick={() => handleRequest(1, 1, 1, 1, 1)}>
        リクエストする
      </button>
    </div>
  );
};

const Header = () => {
  return (
    <header>
      <a href="./index.html" className="logo">
        Billboard Kindai
      </a>
      <nav>
        <ul className="nav-list">
          <li className="nav-list-item">
            <a href="./index.html">ホーム</a>
          </li>
          <li clasName="nav-list-item">
            <a href="./search.html">検索</a>
          </li>
        </ul>
      </nav>
    </header>
  );
};

const container = document.getElementById("root");
const root = ReactDOM.createRoot(container);
root.render(<Result />);
